package io.github.akiart.frostwork.common.block.blockTypes;

import com.mojang.serialization.MapCodec;
import io.github.akiart.frostwork.common.FTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MildewFuzzBlock extends BushBlock {
    public static final MapCodec<MildewFuzzBlock> CODEC = simpleCodec(MildewFuzzBlock::new);

    public MildewFuzzBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || state.is(FTags.Blocks.MOLD);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
}
