package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.GiantFungusFeatureConfig;
import io.github.akiart.frostwork.utils.VoxelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.Set;

public class GiantFungusFeature extends Feature<GiantFungusFeatureConfig> {
    public GiantFungusFeature(Codec<GiantFungusFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<GiantFungusFeatureConfig> context) {
        OptionalInt top;
        var origin = context.origin().mutable();

        try(BulkSectionAccess sectionAccess = new BulkSectionAccess(context.level())) {
            var endRadius = context.config().stemEndRadius().sample(context.random());
            var bottomY = getFloor(sectionAccess, context.origin(), endRadius) - 1;

            if(bottomY < context.level().getMinBuildHeight())
                return false;

            origin.setY(bottomY);
            top = placeStem(context, endRadius, sectionAccess, origin, 8);
        }

        if (top.isEmpty())
            return false;

        placeCap(context, origin, top.getAsInt());
        return true;
    }

    private int getFloor(BulkSectionAccess reader, BlockPos startPos, int width) {
        int[] samples = new int[8];

        samples[0] = projectDown(reader, startPos.offset(-width, 0, 0));
        samples[1] = projectDown(reader, startPos.offset(-width, 0, width));
        samples[2] = projectDown(reader, startPos.offset(-width, 0, -width));

        samples[3] = projectDown(reader, startPos.offset(0, 0, width));
        samples[4] = projectDown(reader, startPos.offset(0, 0, -width));

        samples[5] = projectDown(reader, startPos.offset(width, 0, 0));
        samples[6] = projectDown(reader, startPos.offset(width, 0, width));
        samples[7] = projectDown(reader, startPos.offset(width, 0, -width));

        return Arrays.stream(samples).min().getAsInt();
    }

    private int projectDown(BulkSectionAccess reader, BlockPos offset) {
        BlockPos.MutableBlockPos p = offset.mutable();
        for(int y = offset.getY(); y > 0; --y) {
            p.setY(y);
            if(!canReplace(reader.getBlockState(p))) {
                return y;
            }
        }
        return -9999;
    }

    private boolean canReplace(BlockState state) {
        return !state.blocksMotion() ||
                //state.getBlock() instanceof SpeleothemBlock || use some tag probably
                state.is(BlockTags.ICE);
    }

    private void placeCap(FeaturePlaceContext<GiantFungusFeatureConfig> context, BlockPos origin, int top) {
        var config = context.config();
        var random = context.random();
        var level = context.level();
        var blockPos = origin.above(top);

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

        template.placeInWorld(level, offsetPos, offsetPos, placeSettings, random, Block.UPDATE_CLIENTS);
    }

    private OptionalInt placeStem(FeaturePlaceContext<GiantFungusFeatureConfig> context, int endRadius, BulkSectionAccess sectionAccess, BlockPos origin, int spaceForCap) {

            var random = context.random();
            var stemLength = context.config().stemLength().sample(random);
            var startRadius = context.config().stemStartRadius();

            stemLength = getClearAreaAbove(sectionAccess, context.origin().mutable(), stemLength);

            if (stemLength < context.config().stemLength().getMinValue()) {
                return OptionalInt.empty();
            }

            var mutablePos = origin.above(stemLength);
            var levelOrigin = origin.mutable();

            for (int y = stemLength; y > 0; y--) {
                levelOrigin.setY(origin.getY() + y);

                var radius = Mth.lerpInt(((float) y / stemLength), endRadius, startRadius);
                Set<Vec3i> circle = VoxelUtil.getFilledCircle(radius);

                for (Vec3i p : circle) {
                    mutablePos = levelOrigin.offset(p).mutable();
                    var section = sectionAccess.getSection(mutablePos);

                    if (section != null) {

                        section.setBlockState(
                                SectionPos.sectionRelative(mutablePos.getX()),
                                SectionPos.sectionRelative(mutablePos.getY()),
                                SectionPos.sectionRelative(mutablePos.getZ()),
                                context.config().capBlock().getState(random, mutablePos),
                                false);
                    }
                }
            }

            return OptionalInt.of(stemLength);
    }

    private int getClearAreaAbove(BulkSectionAccess sectionAccess, BlockPos.MutableBlockPos blockPos, int stemLength) {

        for(int y = 0; y < stemLength; y++) {
            if(!sectionAccess.getBlockState(blockPos).isAir())
                return y;

            blockPos.move(Direction.UP);
        }

        return stemLength;
    }
}
