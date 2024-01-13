package io.github.akiart.frostwork.common.worldgen.features.mushroom;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class GrimCapGrower extends AbstractHugeMushroomFeature {
    public GrimCapGrower(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected int getTreeRadiusForHeight(int i, int i1, int i2, int i3) {
        return 0;
    }

    @Override
    protected void makeCap(LevelAccessor levelAccessor, RandomSource random, BlockPos blockPos, int p_225085_, BlockPos.MutableBlockPos mutableBlockPos, HugeMushroomFeatureConfiguration config) {
        for(int i = p_225085_ - 3; i <= p_225085_; ++i) {
            int j = i < p_225085_ ? config.foliageRadius : config.foliageRadius - 1;
            int k = config.foliageRadius - 2;

            for(int l = -j; l <= j; ++l) {
                for(int i1 = -j; i1 <= j; ++i1) {
                    boolean flag = l == -j;
                    boolean flag1 = l == j;
                    boolean flag2 = i1 == -j;
                    boolean flag3 = i1 == j;
                    boolean flag4 = flag || flag1;
                    boolean flag5 = flag2 || flag3;
                    if (i >= p_225085_ || flag4 != flag5) {
                        mutableBlockPos.setWithOffset(blockPos, l, i, i1);
                        if (!levelAccessor.getBlockState(mutableBlockPos).isSolidRender(levelAccessor, mutableBlockPos)) {
                            BlockState blockstate = config.capProvider.getState(random, blockPos);
                            if (blockstate.hasProperty(HugeMushroomBlock.WEST) && blockstate.hasProperty(HugeMushroomBlock.EAST) && blockstate.hasProperty(HugeMushroomBlock.NORTH) && blockstate.hasProperty(HugeMushroomBlock.SOUTH) && blockstate.hasProperty(HugeMushroomBlock.UP)) {
                                blockstate = blockstate
                                        .setValue(HugeMushroomBlock.UP, i >= p_225085_ - 1)
                                        .setValue(HugeMushroomBlock.WEST, l < -k)
                                        .setValue(HugeMushroomBlock.EAST, l > k)
                                        .setValue(HugeMushroomBlock.NORTH, i1 < -k)
                                        .setValue(HugeMushroomBlock.SOUTH, i1 > k);
                            }

                            this.setBlock(levelAccessor, mutableBlockPos, blockstate);
                        }
                    }
                }
            }
        }

    }

}
