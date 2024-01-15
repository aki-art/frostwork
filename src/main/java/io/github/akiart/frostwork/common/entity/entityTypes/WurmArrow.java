package io.github.akiart.frostwork.common.entity.entityTypes;

import io.github.akiart.frostwork.Consts;
import io.github.akiart.frostwork.common.FEntityTypes;
import io.github.akiart.frostwork.common.item.FItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

public class WurmArrow extends AbstractArrow {
    private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack(FItems.TATZELWURM_ARROW.get());

    public WurmArrow(EntityType<? extends WurmArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, DEFAULT_ARROW_STACK);
    }

    public WurmArrow(Level level, double x, double y, double z, ItemStack itemStack) {
        super(FEntityTypes.WURM_ARROW.get(), x, y, z, level, itemStack);
    }

    public WurmArrow(Level level, LivingEntity livingEntity, ItemStack itemStack) {
        super(FEntityTypes.WURM_ARROW.get(), livingEntity, level, itemStack);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            if (this.inGround) {
                if (this.inGroundTime % 5 == 0) {
                    this.makeParticle(1);
                }
            } else {
                this.makeParticle(2);
            }
        } else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
            this.level().broadcastEntityEvent(this, (byte)0);
        }
    }

    public int getColor() {
        return Consts.COLORS.POISON_RESISTANCE;
    }

    private void makeParticle(int pParticleAmount) {
        int i = this.getColor();
        if (i != -1 && pParticleAmount > 0) {
            double d0 = (double)(i >> 16 & 0xFF) / 255.0;
            double d1 = (double)(i >> 8 & 0xFF) / 255.0;
            double d2 = (double)(i >> 0 & 0xFF) / 255.0;

            for(int j = 0; j < pParticleAmount; ++j) {
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), d0, d1, d2);
            }
        }
    }
}
