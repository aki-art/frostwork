package io.github.akiart.frostwork.common.block.blockTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class CandeloupeFruitBlock extends LanternBlock {

    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static BooleanProperty IS_ATTACHED = BooleanProperty.create("is_attached");

    public CandeloupeFruitBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.defaultBlockState()
                .setValue(IS_ATTACHED, true)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HANGING, FACING, IS_ATTACHED, WATERLOGGED);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        // turn towards stem
    }
}
