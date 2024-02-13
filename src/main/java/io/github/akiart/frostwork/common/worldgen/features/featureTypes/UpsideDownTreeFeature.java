package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class UpsideDownTreeFeature extends Feature<TreeConfiguration> {
    private static final int BLOCK_UPDATE_FLAGS = Block.UPDATE_KNOWN_SHAPE
            | Block.UPDATE_CLIENTS
            | Block.UPDATE_NEIGHBORS;

    public UpsideDownTreeFeature(Codec<TreeConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<TreeConfiguration> context) {
        return false;
    }

    private static boolean isVine(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, blockState -> blockState.is(Blocks.VINE));
    }

    public static boolean isAirOrLeaves(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, blockState -> blockState.isAir() || blockState.is(BlockTags.LEAVES));
    }

    private static void setBlockKnownShape(LevelWriter level, BlockPos pPos, BlockState pState) {
        level.setBlock(pPos, pState, BLOCK_UPDATE_FLAGS);
    }

    public static boolean validTreePos(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, blockState -> blockState.isAir() || blockState.is(BlockTags.REPLACEABLE_BY_TREES));
    }

    private int getMaxFreeTreeHeight(LevelSimulatedReader level, int trunkHeight, BlockPos topPosition, TreeConfiguration pConfig) {

        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

        for(int y = 0; y <= trunkHeight + 1; ++y) {
            int size = pConfig.minimumSize.getSizeAtHeight(trunkHeight, y);

            for(int x = -size; x <= size; ++x) {
                for(int z = -size; z <= size; ++z) {
                    blockPos.setWithOffset(topPosition, x, -y, z);
                    if (!pConfig.trunkPlacer.isFree(level, blockPos) || !pConfig.ignoreVines && isVine(level, blockPos))
                        return y + 2;
                }
            }
        }

        return trunkHeight;
    }

}
