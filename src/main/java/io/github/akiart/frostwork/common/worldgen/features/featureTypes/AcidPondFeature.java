package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.block.FBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class AcidPondFeature extends Feature<VegetationPatchConfiguration> {
    public AcidPondFeature(Codec<VegetationPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<VegetationPatchConfiguration> pContext) {

        WorldGenLevel worldgenlevel = pContext.level();
        VegetationPatchConfiguration vegetationpatchconfiguration = pContext.config();
        RandomSource randomsource = pContext.random();
        BlockPos blockpos = pContext.origin();
        Predicate<BlockState> predicate = p_204782_ -> p_204782_.is(vegetationpatchconfiguration.replaceable);
        int i = vegetationpatchconfiguration.xzRadius.sample(randomsource) + 1;
        int j = vegetationpatchconfiguration.xzRadius.sample(randomsource) + 1;
        Set<BlockPos> set = this.placeGroundPatch(worldgenlevel, vegetationpatchconfiguration, randomsource, blockpos, predicate, i, j);
        return !set.isEmpty();

    }
    protected Set<BlockPos> placeGroundPatch(
            WorldGenLevel pLevel,
            VegetationPatchConfiguration pConfig,
            RandomSource pRandom,
            BlockPos pPos,
            Predicate<BlockState> pState,
            int pXRadius,
            int pZRadius
    ) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = pPos.mutable();
        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos$mutableblockpos.mutable();
        Direction direction = pConfig.surface.getDirection();
        Direction direction1 = direction.getOpposite();
        Set<BlockPos> set = new HashSet<>();

        for(int i = -pXRadius; i <= pXRadius; ++i) {
            boolean flag = i == -pXRadius || i == pXRadius;

            for(int j = -pZRadius; j <= pZRadius; ++j) {
                boolean flag1 = j == -pZRadius || j == pZRadius;
                boolean flag2 = flag || flag1;
                boolean flag3 = flag && flag1;
                boolean flag4 = flag2 && !flag3;
                if (!flag3 && (!flag4 || pConfig.extraEdgeColumnChance != 0.0F && !(pRandom.nextFloat() > pConfig.extraEdgeColumnChance))) {
                    blockpos$mutableblockpos.setWithOffset(pPos, i, 0, j);

                    for(int k = 0;
                        pLevel.isStateAtPosition(blockpos$mutableblockpos, BlockBehaviour.BlockStateBase::isAir) && k < pConfig.verticalRange;
                        ++k
                    ) {
                        blockpos$mutableblockpos.move(direction);
                    }

                    for(int i1 = 0;
                        pLevel.isStateAtPosition(blockpos$mutableblockpos, p_284926_ -> !p_284926_.isAir()) && i1 < pConfig.verticalRange;
                        ++i1
                    ) {
                        blockpos$mutableblockpos.move(direction1);
                    }

                    blockpos$mutableblockpos1.setWithOffset(blockpos$mutableblockpos, pConfig.surface.getDirection());
                    BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos1);
                    if (pLevel.isEmptyBlock(blockpos$mutableblockpos)
                            && blockstate.isFaceSturdy(pLevel, blockpos$mutableblockpos1, pConfig.surface.getDirection().getOpposite())) {
                        int l = pConfig.depth.sample(pRandom)
                                + (pConfig.extraBottomBlockChance > 0.0F && pRandom.nextFloat() < pConfig.extraBottomBlockChance ? 1 : 0);
                        BlockPos blockpos = blockpos$mutableblockpos1.immutable();
                        boolean flag5 = this.placeGround(pLevel, pConfig, pState, pRandom, blockpos$mutableblockpos1, l);
                        if (flag5) {
                            set.add(blockpos);
                        }
                    }
                }
            }
        }

        Set<BlockPos> set1 = new HashSet<>();
        BlockPos.MutableBlockPos blockpos$mutableblockpos2 = new BlockPos.MutableBlockPos();

        for(BlockPos blockpos : set) {
            if (!isExposed(pLevel, set, blockpos, blockpos$mutableblockpos2)) {
                set1.add(blockpos);
            }
        }

        for(BlockPos blockpos1 : set1) {
            pLevel.setBlock(blockpos1, FBlocks.ACID.get().defaultBlockState(), 2);
        }


        return set1;
    }

    private static boolean isExposed(WorldGenLevel pLevel, Set<BlockPos> pPositions, BlockPos pPos, BlockPos.MutableBlockPos pMutablePos) {
        return isExposedDirection(pLevel, pPos, pMutablePos, Direction.NORTH)
                || isExposedDirection(pLevel, pPos, pMutablePos, Direction.EAST)
                || isExposedDirection(pLevel, pPos, pMutablePos, Direction.SOUTH)
                || isExposedDirection(pLevel, pPos, pMutablePos, Direction.WEST)
                || isExposedDirection(pLevel, pPos, pMutablePos, Direction.DOWN);
    }

    private static boolean isExposedDirection(WorldGenLevel pLevel, BlockPos pPos, BlockPos.MutableBlockPos pMutablePos, Direction pDirection) {
        pMutablePos.setWithOffset(pPos, pDirection);
        return !pLevel.getBlockState(pMutablePos).isFaceSturdy(pLevel, pMutablePos, pDirection.getOpposite());
    }
    protected boolean placeGround(
            WorldGenLevel pLevel,
            VegetationPatchConfiguration pConfig,
            Predicate<BlockState> pReplaceableblocks,
            RandomSource pRandom,
            BlockPos.MutableBlockPos pMutablePos,
            int pMaxDistance
    ) {
        for(int i = 0; i < pMaxDistance; ++i) {
            BlockState blockstate = pConfig.groundState.getState(pRandom, pMutablePos);
            BlockState blockstate1 = pLevel.getBlockState(pMutablePos);
            if (!blockstate.is(blockstate1.getBlock())) {
                if (!pReplaceableblocks.test(blockstate1)) {
                    return i != 0;
                }

                pLevel.setBlock(pMutablePos, blockstate, 2);
                pMutablePos.move(pConfig.surface.getDirection());
            }
        }

        return true;
    }
}
