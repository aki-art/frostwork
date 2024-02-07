package io.github.akiart.frostwork.common.block.blockTypes;

import io.github.akiart.frostwork.common.block.FBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OvergrownMildewBlock extends Block {
    //public static IntegerProperty GROWTH = IntegerProperty.create("growth", 0, 1);
    public OvergrownMildewBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction direction, @Nullable LivingEntity igniter) {
        super.onCaughtFire(state, level, pos, direction, igniter);

        level.setBlock(pos, FBlocks.SANGUITE.block.get().defaultBlockState(), Block.UPDATE_CLIENTS);
    }
}
