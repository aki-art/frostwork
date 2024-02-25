package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.StemmedPlantFeatureConfig;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.block.Block;

public class StemmedPlantFeature extends Feature<StemmedPlantFeatureConfig> {
    public StemmedPlantFeature(Codec<StemmedPlantFeatureConfig> codec) {
        super(codec);
    }

    private static Direction[] VALID_GROWTH_DIRECTIONS = new Direction[] {
            Direction.EAST,
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST
    };

    @Override
    public boolean place(FeaturePlaceContext<StemmedPlantFeatureConfig> context) {
        var stem = context.config().stem().getState(context.random(), context.origin());
        var level = context.level();

        if(stem.hasProperty(AttachedStemBlock.FACING)) {
            for(var direction : Util.shuffledCopy(VALID_GROWTH_DIRECTIONS, context.random())) {
                var fruitPos = context.origin().relative(direction);

                if(level.getBlockState(fruitPos.below()).isSolid()) {
                    var fruit = context.config().fruit().getState(context.random(), fruitPos);
                    level.setBlock(fruitPos, fruit.setValue(AttachedStemBlock.FACING, direction.getOpposite()), Block.UPDATE_CLIENTS);
                    level.setBlock(context.origin(), stem.setValue(AttachedStemBlock.FACING, direction), Block.UPDATE_CLIENTS);

                    return true;
                }
            }
        }
        else
            Frostwork.LOGGER.warn("Trying to place StemmedPlantFeature but the provided block does not have a FACING property.");

        return false;
    }
}
