package io.github.akiart.frostwork.common.block.blockTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

// the tree it grows can decide to grow up or down on its own so the sapling just needs to visually look right
public class FlippableSaplingBlock extends SaplingBlock {
    public static DirectionProperty VERTICAL_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;

    public FlippableSaplingBlock(TreeGrower treeGrover, Properties properties) {
        super(treeGrover, properties);

        this.registerDefaultState(this.defaultBlockState()
                .setValue(VERTICAL_DIRECTION, Direction.UP));
    }

    // TODO: update shape

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelaccessor = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction preferredDirection = context.getNearestLookingVerticalDirection().getOpposite();
        Direction viableDirection = calculateSaplingDirection(levelaccessor, blockpos, preferredDirection);

        if (viableDirection == null)
            return null;

        return this.defaultBlockState().setValue(VERTICAL_DIRECTION, viableDirection);
    }

    @Override
    public boolean canSurvive(BlockState pState, @NotNull LevelReader level, BlockPos pPos) {
        Direction plantedBlockDirection = pState.getValue(VERTICAL_DIRECTION).getOpposite();
        BlockPos blockpos = pPos.relative(plantedBlockDirection.getOpposite());

        // Forge: This function is called during world gen and placement, before this block is set, so if we are not 'here' then assume it's the pre-check.
        if (pState.getBlock() == this)
            return level.getBlockState(blockpos).canSustainPlant(level, blockpos, plantedBlockDirection.getOpposite(), this);

        return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
    }

    @Nullable
    private Direction calculateSaplingDirection(LevelReader level, BlockPos saplingPos, Direction preferredDirection) {
        BlockState preferredTargetState = level.getBlockState(saplingPos.relative(preferredDirection));

        if (mayPlaceOn(preferredTargetState, level, saplingPos))
            return preferredDirection;

        BlockState fallbackTargetState = level.getBlockState(saplingPos.relative(preferredDirection.getOpposite()));

        if (!mayPlaceOn(fallbackTargetState, level, saplingPos))
            return null;

        return preferredDirection.getOpposite();
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(VERTICAL_DIRECTION);
    }
}
