package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.PillarFeatureConfig;
import io.github.akiart.frostwork.utils.VoxelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Arrays;
import java.util.Set;

// slower than mojang's solution, but more customizable and doesn't leave random floating pillars
public class PillarFeature extends Feature<PillarFeatureConfig> {
    public PillarFeature(Codec<PillarFeatureConfig> codec) {
        super(codec);
    }

    private int getFloor(WorldGenLevel reader, BlockPos startPos, int width) {
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

    private int projectUp(WorldGenLevel reader, BlockPos offset) {
        BlockPos.MutableBlockPos p = offset.mutable();
        for(int y = offset.getY(); y < 255; y++) {
            p.setY(y);
            if(!canReplace(reader.getBlockState(p))) {
                return y;
            }
        }

        return -1;
    }

    private int getCeiling(WorldGenLevel reader, BlockPos startPos, int width) {
        int[] samples = new int[8];

        samples[0] = projectUp(reader, startPos.offset(-width, 0, 0));
        samples[1] = projectUp(reader, startPos.offset(-width, 0, width));
        samples[2] = projectUp(reader, startPos.offset(-width, 0, -width));

        samples[3] = projectUp(reader, startPos.offset(0, 0, width));
        samples[4] = projectUp(reader, startPos.offset(0, 0, -width));

        samples[5] = projectUp(reader, startPos.offset(width, 0, 0));
        samples[6] = projectUp(reader, startPos.offset(width, 0, width));
        samples[7] = projectUp(reader, startPos.offset(width, 0, -width));

        return Arrays.stream(samples).max().getAsInt();
    }

    private int projectDown(WorldGenLevel reader, BlockPos offset) {
        BlockPos.MutableBlockPos p = offset.mutable();
        for(int y = offset.getY(); y > 0; --y) {
            p.setY(y);
            if(!canReplace(reader.getBlockState(p))) {
                return y;
            }
        }
        return -1;
    }

    private boolean canReplace(BlockState state) {
        return !state.blocksMotion() ||
                //state.getBlock() instanceof SpeleothemBlock ||
                state.is(BlockTags.ICE);
    }


    @Override
    public boolean place(FeaturePlaceContext<PillarFeatureConfig> context) {

        var config = context.config();
        var random = context.random();
        var reader = context.level();
        var startPos = context.origin();

        int width = config.width().sample(random);


        int ceiling = getCeiling(reader, startPos, width);
        int floor = getFloor(reader, startPos, width);
        int length = ceiling - floor;

        startPos = new BlockPos(startPos.getX(), ceiling, startPos.getZ());

        Set<Vec3i> circle = VoxelUtil.getFilledCircle(width);
        for (Vec3i p : circle) {
            BlockPos.MutableBlockPos pos = new BlockPos(p).offset(startPos).mutable();
            int segmentLength = getSegmentLength(p.getX(), p.getZ(), width, length);
            segmentLength = (int)Math.min(segmentLength, length / 2f); // make it not double place blocks of the overlapping halves

            for (int y = startPos.getY(); y >= startPos.getY() - segmentLength; --y) {
                pos.setY(y);
                reader.setBlock(pos, config.state().getState(random, pos), Block.UPDATE_CLIENTS);
            }

            for (int y = floor; y <= floor + segmentLength; ++y) {
                pos.setY(y);
                reader.setBlock(pos, config.state().getState(random, pos), Block.UPDATE_CLIENTS);
            }
        }

        return true;
    }

    private int getSegmentLength(float x, float z, float width, float height) {
        width *= 2f;

        double y = Math.exp(-(((x * x) / width + ((z * z) / width))));
        return Mth.floor(y * height);
    }
}
