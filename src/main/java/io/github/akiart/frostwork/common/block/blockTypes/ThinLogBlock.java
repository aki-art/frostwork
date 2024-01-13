package io.github.akiart.frostwork.common.block.blockTypes;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class ThinLogBlock extends PipeBlock implements SimpleWaterloggedBlock  {
    public static final MapCodec<ThinLogBlock> CODEC = simpleCodec(ThinLogBlock::new);

    public ThinLogBlock(Properties properties) {
        super(0.3f, properties);

        registerDefaultState(defaultBlockState()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, true)
                .setValue(DOWN, true)
                .setValue(BlockStateProperties.WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    protected boolean canConnectTo(BlockState blockState) {
        Block block = blockState.getBlock();
        return block == this || blockState.is(BlockTags.LEAVES) || block instanceof  LeavesBlock;
    }

    protected boolean isDirt(BlockState blockState) {
        return blockState.is(BlockTags.DIRT);
    }

    @Override
    public BlockState updateShape(BlockState thisState, Direction direction, BlockState thatState, LevelAccessor world, BlockPos thisBlockState, BlockPos thatBlockState) {
        boolean isConnecting = canConnectTo(thatState) || direction == Direction.DOWN && isDirt(thatState);
        return thisState.setValue(PROPERTY_BY_DIRECTION.get(direction), isConnecting);
    }

    @Override
    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType computationType) {
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level reader = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        //FluidState fluidState = reader.getFluidState(blockPos);

        return ChorusPlantBlock.getStateWithConnections(reader, blockPos, this.defaultBlockState());
    }

    @Override
    protected MapCodec<? extends PipeBlock> codec() {
        return CODEC;
    }
}
