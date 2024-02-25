package io.github.akiart.frostwork.common.block.blockTypes;

import io.github.akiart.frostwork.client.particles.FParticles;
import io.github.akiart.frostwork.common.block.FBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class AirBubbleColumnBlock extends Block {

    private static final int BUBBLE_POP_SOUND_CHANCE = 400;
    private static final float BUBBLE_Y_SPEED = 0.02f;

    public AirBubbleColumnBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(@NotNull BlockState blockState, Level level, BlockPos pos, @NotNull Entity entity) {
        BlockState blockstate = level.getBlockState(pos.above());
        if (blockstate.isAir()) {

            entity.onAboveBubbleCol(false); // todo: capability for slower upward movement

            if (!level.isClientSide) {
                ServerLevel serverlevel = (ServerLevel) level;

                for(int i = 0; i < 2; ++i) {

                    serverlevel.sendParticles(
                            ParticleTypes.SPLASH,
                            (double) pos.getX() + level.random.nextDouble(),
                            pos.getY() + 1,
                            (double) pos.getZ() + level.random.nextDouble(),
                            1,
                            0.0,
                            0.0,
                            0.0,
                            1.0
                    );

                    serverlevel.sendParticles(
                            FParticles.AIR_BUBBLE.get(),
                            (double) pos.getX() + level.random.nextDouble(),
                            pos.getY() + 1,
                            (double) pos.getZ() + level.random.nextDouble(),
                            1,
                            0.0,
                            0.01,
                            0.0,
                            0.2
                    );
                }
            }
        } else {
            //entity.onInsideBubbleColumn(false);

            Vec3 vec3 = entity.getDeltaMovement();
            double d0 = Math.min(0.3, vec3.y + 0.06);

            entity.setDeltaMovement(vec3.x, d0, vec3.z);
            entity.resetFallDistance();
        }
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
        updateColumn(level, pos, blockState, level.getBlockState(pos.below()));
    }

    public static void updateColumn(LevelAccessor level, BlockPos pos, BlockState blockStateBelow) {
        updateColumn(level, pos, level.getBlockState(pos), blockStateBelow);
    }

    public static void updateColumn(LevelAccessor level, BlockPos pos, BlockState blockStateIn, BlockState blockStateBelow) {
        if (!canExistIn(blockStateIn))
            return;

        BlockState blockstate = getColumnState(blockStateBelow);
        level.setBlock(pos, blockstate, Block.UPDATE_CLIENTS);
        BlockPos.MutableBlockPos blockPosAbove = pos.mutable().move(Direction.UP);

        while(canExistIn(level.getBlockState(blockPosAbove))) {
            if (!level.setBlock(blockPosAbove, blockstate, Block.UPDATE_CLIENTS))
                return;

            blockPosAbove.move(Direction.UP);
        }
    }

    private static BlockState getColumnState(BlockState blockStateBelow) {
        if (blockStateBelow.is(FBlocks.AIR_BUBBLE_COLUMN) || blockStateBelow.is(FBlocks.BUBBLE_VENT))
            return FBlocks.AIR_BUBBLE_COLUMN.get().defaultBlockState();

        return Blocks.AIR.defaultBlockState();
    }

    private static boolean canExistIn(BlockState blockStateBelow) {
        return blockStateBelow.is(FBlocks.AIR_BUBBLE_COLUMN) || blockStateBelow.isAir();
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos.below());
        return blockstate.is(this) || blockstate.is(FBlocks.BUBBLE_VENT);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }


    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        level.addAlwaysVisibleParticle(
                FParticles.AIR_BUBBLE.get(),
                x + 0.5,
                y,
                z + 0.5,
                0.0,
                BUBBLE_Y_SPEED,
                0.0);

        level.addAlwaysVisibleParticle(
                FParticles.AIR_BUBBLE.get(),
                x + (double) random.nextFloat(),
                y + (double) random.nextFloat(),
                z + (double) random.nextFloat(),
                0.0,
                BUBBLE_Y_SPEED,
                0.0
        );

        if (random.nextInt(200) == 0) {
            level.playLocalSound(
                    x,
                    y,
                    z,
                    SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT,
                    SoundSource.BLOCKS,
                    0.1F + random.nextFloat() * 0.2F,
                    1.1F + random.nextFloat() * 0.15F,
                    false
            );
        }
    }
}
