package io.github.akiart.frostwork.common.block.blockTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class IcicleBlock extends SpeleothemBlock{
    public IcicleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        // todo
        return super.canSurvive(state, level, pos);
    }
}
