package io.github.akiart.frostwork.common.block.blockTypes;

import com.mojang.serialization.MapCodec;
import io.github.akiart.frostwork.common.item.FItems;
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.EmptyFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FoamBlock extends Block {
    public static final MapCodec<FoamBlock> CODEC = simpleCodec(FoamBlock::new);

    public static final int MAX_FOAM = 4;
    public static final IntegerProperty FOAM_AMOUNT = IntegerProperty.create("fw_foam_amount", 1, MAX_FOAM);
    protected static final VoxelShape AABB = Block.box(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if (itemstack.is(Items.GLASS_BOTTLE)) {
                itemstack.shrink(1);

                decreaseFoamLevel(pState, pLevel, pPos);

                pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                Item bottle = FItems.BOTTLE_OF_FOAM.get();

                if (itemstack.isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(bottle));
                } else if (!pPlayer.getInventory().add(new ItemStack(bottle))) {
                    pPlayer.drop(new ItemStack(bottle), false);
                }

                pPlayer.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                pLevel.gameEvent(pPlayer, GameEvent.FLUID_PICKUP, pPos);

                return InteractionResult.CONSUME;
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    private static void decreaseFoamLevel(BlockState blockState, Level level, BlockPos blockPos) {
        int foamLevel = blockState.getValue(FOAM_AMOUNT);
        if(foamLevel == 1) {
            level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
        else {
            level.setBlock(blockPos, blockState.setValue(FOAM_AMOUNT, Math.max(1, blockState.getValue(FOAM_AMOUNT)) - 1), 2);
        }
    }

    private static boolean tryIncreaseFoamLevel(BlockState blockState, Level level, BlockPos blockPos) {
        if(blockState.getValue(FOAM_AMOUNT) == MAX_FOAM)
            return false;

        level.setBlock(blockPos, blockState.setValue(FOAM_AMOUNT, Math.min(MAX_FOAM, blockState.getValue(FOAM_AMOUNT)) + 1), 2);

        return true;
    }

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
