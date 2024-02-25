package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.VelwoodTreeFeatureConfig;
import io.github.akiart.frostwork.utils.TemplateUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.function.Predicate;

// todo: area check to make sure the foliage fits
public class VelwoodTreeFeature extends Feature<VelwoodTreeFeatureConfig> {
    private static final int BLOCK_UPDATE_FLAGS = Block.UPDATE_KNOWN_SHAPE
            | Block.UPDATE_CLIENTS
            | Block.UPDATE_NEIGHBORS;

    private static final Predicate<BlockState> IS_REPLACEABLE = state -> state.is(Blocks.AIR) || state.is(BlockTags.REPLACEABLE_BY_TREES);
    public VelwoodTreeFeature(Codec<VelwoodTreeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<VelwoodTreeFeatureConfig> context) {


        var origin = context.origin();
        var level = context.level();

       // level.setBlock(origin, Blocks.VERDANT_FROGLIGHT.defaultBlockState(), 3);
        if(!TreeFeature.validTreePos(level, origin))
            return false;

        var trunkHeight = context.config().height().sample(context.random());

        var below = getSpace(origin, level, trunkHeight, Direction.DOWN, 4);
        var above = getSpace(origin, level, trunkHeight, Direction.UP, 4);

        var minHeight = context.config().height().getMinValue();

        if((below == 0) == (above == 0) || below + above < minHeight)
            return false;

        var growthDirection = below < above ? Direction.UP : Direction.DOWN;

        grow(origin, level, context.random(), context.config(), trunkHeight, growthDirection);
        placeFoliage(origin, trunkHeight, context, growthDirection);

        return true;
    }

    private boolean placeFoliage(BlockPos origin, int trunkHeight, FeaturePlaceContext<VelwoodTreeFeatureConfig> context, Direction growthDirection) {
        var blockPos = origin.relative(growthDirection, trunkHeight).mutable();
        var level = context.level();
        var config = context.config();
        var random = context.random();

        var list = growthDirection == Direction.UP ? config.foliageStructuresUpwards() : config.foliageStructuresDownwards();
        var foliageCount = list.size();
        var foliagePreset = list.get(random.nextInt(foliageCount));
        var rotation = Rotation.getRandom(random);
        var offset = foliagePreset.offset();

        var templateManager = level.getLevel().getServer().getStructureManager();
        var template = templateManager.getOrCreate(foliagePreset.structure());

        var chunkPos = new ChunkPos(blockPos);

        var widthOffet = config.width() - 1;

        var centerPos = blockPos.offset(-offset.getX() - widthOffet, 0, -offset.getZ() - widthOffet);

        var placeSettings = new StructurePlaceSettings()
                .setRotation(rotation)
                .setRotationPivot(new BlockPos(offset))
                .setBoundingBox(TemplateUtil.getChunkBox(chunkPos, level))
                .setRandom(random);

        // template.placeInWorld(level, centerPos, centerPos, placeSettings, random, BLOCK_UPDATE_FLAGS);
        return TemplateUtil.placeInWorld(template, level, centerPos, centerPos, placeSettings, random, IS_REPLACEABLE, BLOCK_UPDATE_FLAGS);
    }

    private void grow(BlockPos startPos, WorldGenLevel level, RandomSource random, VelwoodTreeFeatureConfig config, int trunkHeight, Direction direction) {
        var mutablePos = startPos.mutable();

        for(var y = 0; y < trunkHeight; y++) {

            for(var x = 0; x < config.width(); x++) {
                mutablePos.setX(startPos.getX() + x);

                for(var z = 0; z < config.width(); z++) {
                    mutablePos.setZ(startPos.getZ() + z);

                    level.setBlock(mutablePos, config.trunk().getState(random, mutablePos), BLOCK_UPDATE_FLAGS);
                }
            }

            mutablePos.move(direction);
        }
    }

    private boolean isReplaceable(BlockState state) {
        return state.isAir() || state.is(BlockTags.REPLACEABLE_BY_TREES);
    }

    private int getSpace(BlockPos origin, WorldGenLevel level, int trunkHeight, Direction direction, int spaceForFoliage) {
        var mutablePos = origin.mutable();

        for(var y = 0; y < trunkHeight; y++) {
            mutablePos.move(direction);

            if(!isReplaceable(level.getBlockState(mutablePos)))
                return y;
        }

        return Math.max(0, trunkHeight - spaceForFoliage);
    }
}
