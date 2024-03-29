package io.github.akiart.frostwork.common.block.blockTypes;

import io.github.akiart.frostwork.client.particles.FParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AcidLiquidBlock extends LiquidBlock {

    public AcidLiquidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
        super(supplier, properties);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

//    @Override
//    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
//
//        level.addParticle(ParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 0, 0.1f, 0);
//        return true;
//    }
    @Nullable
    public BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {

        // TODO: acid navigatoin for acid immunes
        return BlockPathTypes.DAMAGE_OTHER;
    }

    @Nullable
    public BlockPathTypes getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, BlockPathTypes originalType) {
        return BlockPathTypes.DANGER_OTHER;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!world.isClientSide()) return;

        BlockPos abovePos = pos.above();
        if (!world.getBlockState(abovePos).isAir()) {
            if (!world.getBlockState(abovePos).isSolidRender(world, abovePos) && random.nextInt(20) == 0) {

                spawnBubbleParticle(world, pos, random);
            }

            if (random.nextInt(200) == 0) {
                //world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 1.5F + random.nextFloat() * 0.15F, false);
            }
        }
        else {
            if(random.nextInt(10) == 0)
                spawnBubbleParticle(world, pos, random);

            // only play sound at surface
            if (random.nextInt(200) == 0) {
                world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 0.5F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }
        }
    }

    private static void spawnBubbleParticle(Level world, BlockPos pos, RandomSource random) {
        double x = (double) pos.getX() + random.nextDouble();
        double y = pos.getY();
        double z = (double) pos.getZ() + random.nextDouble();

        double xs = (random.nextDouble() - .5d) * .2d;
        double ys = random.nextDouble() * .2d;
        double zs = (random.nextDouble() - .5d) * .2d;

        world.addParticle(FParticles.ACID_BUBBLE.get(), x, y, z, xs, ys, zs);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
//        // update acid capability
//        pEntity.getCapability(Capabilities.ACID).ifPresent(cap -> {
//            cap.setIsInAcid(true);
//        });
//
//        // acid damage
//        if (pEntity.hurt(FDamageSource.ACID, 2f)) {
//            pEntity.playSound(SoundEvents.GENERIC_BURN, 1f, 1f);
//        }
    }
}
