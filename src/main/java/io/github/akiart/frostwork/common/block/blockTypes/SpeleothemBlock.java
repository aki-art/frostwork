package io.github.akiart.frostwork.common.block.blockTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

// Similar to dripstone block, but more generic
// todo: voxelshape
// todo: waterdrip
// todo: falling
public class SpeleothemBlock extends Block implements Fallable {
    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
    private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape BASE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    private static final float MAX_HORIZONTAL_OFFSET = 0.125f;

   // public static final RotatableBoxVoxelShape SMALL_SHAPE = RotatableBoxVoxelShape.createXZSymmetric(3.0, 0, 12.0);
    public SpeleothemBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(TIP_DIRECTION, Direction.UP)
                        .setValue(THICKNESS, DripstoneThickness.TIP)
                        .setValue(WATERLOGGED, false)
        );
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var clickedPos = context.getClickedPos();
        var direction = context.getNearestLookingVerticalDirection().getOpposite();
        var growthDirection = calculateTipDirection(level, clickedPos, direction);

        if(growthDirection == null)
            return null;

        var isSecondaryUseInactive = !context.isSecondaryUseActive();
        var thickness = calculateThickness(level, clickedPos, growthDirection, isSecondaryUseInactive);

        if(thickness == null)
            return null;

        return this.defaultBlockState()
                .setValue(TIP_DIRECTION, growthDirection)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, level.getFluidState(clickedPos).getType() == Fluids.WATER);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter level, BlockPos pos, CollisionContext context) {
        DripstoneThickness dripstonethickness = pState.getValue(THICKNESS);
        VoxelShape voxelshape;
        if (dripstonethickness == DripstoneThickness.TIP_MERGE) {
            voxelshape = TIP_MERGE_SHAPE;
        } else if (dripstonethickness == DripstoneThickness.TIP) {
            if (pState.getValue(TIP_DIRECTION) == Direction.DOWN) {
                voxelshape = TIP_SHAPE_DOWN;
            } else {
                voxelshape = TIP_SHAPE_UP;
            }
        } else if (dripstonethickness == DripstoneThickness.FRUSTUM) {
            voxelshape = FRUSTUM_SHAPE;
        } else if (dripstonethickness == DripstoneThickness.MIDDLE) {
            voxelshape = MIDDLE_SHAPE;
        } else {
            voxelshape = BASE_SHAPE;
        }

        Vec3 vec3 = pState.getOffset(level, pos);
        return voxelshape.move(vec3.x, 0.0, vec3.z);
    }

    @Nullable
    private Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction preferredDirection) {
        if(isValidPointedDripstonePlacement(level, pos, preferredDirection))
            return preferredDirection;

        var opposite = preferredDirection.getOpposite();
        if(isValidPointedDripstonePlacement(level, pos, opposite))
            return opposite;

        return null;
    }

    private boolean isValidPointedDripstonePlacement(LevelReader level, BlockPos pos, Direction direction) {
        var blockpos = pos.relative(direction.getOpposite());
        var blockstate = level.getBlockState(blockpos);

        return blockstate.isFaceSturdy(level, blockpos, direction)
                || isPointedDripstoneWithDirection(blockstate, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Override
    public BlockState updateShape(BlockState state,
                                  Direction direction,
                                  BlockState neighborState,
                                  LevelAccessor level,
                                  BlockPos pos,
                                  BlockPos nighborPos) {

        if (state.getValue(WATERLOGGED))
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        if(direction != Direction.UP && direction != Direction.DOWN)
            return state;

        var tipDirection = state.getValue(TIP_DIRECTION);
        if(direction == Direction.DOWN && level.getBlockTicks().hasScheduledTick(pos, this))
            return state;

        if(direction == tipDirection.getOpposite() && !canSurvive(state, level, pos)) {
            level.scheduleTick(pos, this, tipDirection == Direction.DOWN ? 2 : 1);
            return state;
        }

        var isMergedTip = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
        return state.setValue(THICKNESS, calculateThickness(level, pos, tipDirection, isMergedTip));
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    private boolean isPointedDripstoneWithDirection(BlockState state, Direction direction) {
        return state.is(this)
                && state.getValue(TIP_DIRECTION) == direction;
    }

    private DripstoneThickness calculateThickness(LevelReader level, BlockPos pos, Direction direction, boolean tipsMerge) {
        var opposite = direction.getOpposite();
        var blockStateAhead = level.getBlockState(pos.relative(direction));

        if(isPointedDripstoneWithDirection(blockStateAhead, opposite))
            return !tipsMerge && blockStateAhead.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE
                    ? DripstoneThickness.TIP
                    : DripstoneThickness.TIP_MERGE;

        if(!isPointedDripstoneWithDirection(blockStateAhead, direction))
            return DripstoneThickness.TIP;

        var thicknessAhead = blockStateAhead.getValue(THICKNESS);
        if(thicknessAhead != DripstoneThickness.TIP && thicknessAhead != DripstoneThickness.TIP_MERGE)
        {
            var blockStateBehind = level.getBlockState(pos.relative(opposite));
            return !isPointedDripstoneWithDirection(blockStateBehind, direction)
                    ? DripstoneThickness.BASE
                    : DripstoneThickness.MIDDLE;
        }

        return DripstoneThickness.FRUSTUM;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public float getMaxHorizontalOffset() {
        return MAX_HORIZONTAL_OFFSET;
    }
}
