package io.github.akiart.frostwork.common.block.blockTypes;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FoamBlock extends Block {
    public static final MapCodec<FoamBlock> CODEC = simpleCodec(FoamBlock::new);
    public static final IntegerProperty FOAM_AMOUNT = IntegerProperty.create("fw_foam_amount", 1, 4);
    protected static final VoxelShape AABB = Block.box(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    @Override
    public MapCodec<FoamBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        FluidState fluidOn = pLevel.getFluidState(pPos);
        FluidState fluidBelow = pLevel.getFluidState(pPos.below());
        return fluidBelow.getType() == Fluids.WATER && fluidOn.getType() == Fluids.EMPTY; // todo: acid
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    @Override
    public boolean canBeReplaced(BlockState blockState, BlockPlaceContext context) {
        return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && blockState.getValue(FOAM_AMOUNT) < 4
                ? true
                : super.canBeReplaced(blockState, context);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return blockstate.is(this)
                ? blockstate.setValue(FOAM_AMOUNT, Integer.valueOf(Math.min(4, blockstate.getValue(FOAM_AMOUNT) + 1)))
                : this.defaultBlockState();
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);

        if (pLevel instanceof ServerLevel && pEntity instanceof Boat) {
            pLevel.destroyBlock(new BlockPos(pPos), true, pEntity);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FOAM_AMOUNT);
    }

    public FoamBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FOAM_AMOUNT, 1));
    }
}
