package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.BigFungusFeatureConfig;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class SimpleBigFungusFeature extends Feature<BigFungusFeatureConfig> {
    public SimpleBigFungusFeature(Codec<BigFungusFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BigFungusFeatureConfig> context) {
        var config = context.config();
        var random = context.random();
        var stemLength = config.stemLength().sample(context.random());
        var level = context.level();

        var origin = context.origin();
        var blockPos = origin.mutable();

        for(int y = 0; y < stemLength; y++) {
            level.setBlock(blockPos, config.stemBlock().getState(random, blockPos), Block.UPDATE_CLIENTS);
            blockPos.move(Direction.UP);
        }

        blockPos.move(Direction.UP, config.capOffset() - 1);

        var capCount = config.capStructures().size();
        var structure = config.capStructures().get(capCount <= 1 ? 0 : random.nextInt(capCount - 1));
        var templateManager = level.getLevel().getServer().getStructureManager();
        var template = templateManager.getOrCreate(structure);
        var chunkPos = new ChunkPos(blockPos);

        var boundingBox = new BoundingBox(
                chunkPos.getMinBlockX() - 16,
                level.getMinBuildHeight(),
                chunkPos.getMinBlockZ() - 16,
                chunkPos.getMaxBlockX() + 16,
                level.getMaxBuildHeight(),
                chunkPos.getMaxBlockZ() + 16
        );

        var placeSettings = new StructurePlaceSettings().setBoundingBox(boundingBox).setRandom(random);
        var size = template.getSize();
        var centerPos = blockPos.offset(-size.getX() / 2, 0, -size.getZ() / 2);
        var offsetPos = template.getZeroPositionWithTransform(centerPos.atY(blockPos.getY()), Mirror.NONE, Rotation.NONE);

        template.placeInWorld(level, offsetPos, offsetPos, placeSettings, random, Block.UPDATE_INVISIBLE);

        return true;
    }
}
