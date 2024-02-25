package io.github.akiart.frostwork.common.block.blockTypes;

import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class SoapBlock extends LayeredBlock {
    protected final Supplier<MobEffect> mobEffect;
    protected final int duration;

    public SoapBlock(Properties properties, Supplier<MobEffect> mobEffect, int duration) {
        super(properties);
        this.mobEffect = mobEffect;
        this.duration = duration;
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        applyEffect(level, blockState, blockPos, entity);
    }

    private void applyEffect(Level level, BlockState blockState, BlockPos blockPos, Entity entity) {
        if (mobEffect != null
                && !entity.isSteppingCarefully()
                && entity instanceof LivingEntity livingEntity
                && !livingEntity.hasEffect(mobEffect.get())) {

            if(level.isClientSide)
                spawnBubbles(level, blockPos, entity);

            livingEntity.addEffect(new MobEffectInstance(mobEffect.get(), duration * 20));

            int layer = blockState.getValue(LAYERS);

            if(layer == 1)
                level.removeBlock(blockPos,false);
            else
                level.setBlock(blockPos, blockState.setValue(LAYERS, layer - 1), Block.UPDATE_ALL);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {

        Frostwork.LOGGER.info("sfdfdf");

        applyEffect(level, state, pos, entity);
        super.stepOn(level, pos, state, entity);
    }

    private void spawnBubbles(Level level, BlockPos pos, Entity entity) {

    }
}
