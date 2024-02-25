package io.github.akiart.frostwork.common.entity.entityTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class ThrownLavaPotion extends ThrownPotion {
    public ThrownLavaPotion(EntityType<? extends ThrownPotion> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownLavaPotion(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
    }

    public ThrownLavaPotion(Level pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }


    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!this.level().isClientSide) {
            ItemStack itemstack = this.getItem();
            Potion potion = PotionUtils.getPotion(itemstack);
            List<MobEffectInstance> list = PotionUtils.getMobEffects(itemstack);
            Direction direction = pResult.getDirection();
            BlockPos blockpos = pResult.getBlockPos();
            BlockPos blockpos1 = blockpos.relative(direction);

            var blockState = this.level().getBlockState(blockpos);
            if(blockState.isAir())
            {
                this.level().setBlockAndUpdate(blockpos, Blocks.LAVA.defaultBlockState().setValue(LiquidBlock.LEVEL, 2));
                this.level().playSound(null, blockpos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
                this.level().gameEvent(null, GameEvent.FLUID_PLACE, blockpos);
            }
        }
    }
}
