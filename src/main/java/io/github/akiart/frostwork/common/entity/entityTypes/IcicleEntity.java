//package io.github.akiart.frostwork.common.entity.entityTypes;
//
//import io.github.akiart.frostwork.common.item.FItems;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.entity.projectile.AbstractArrow;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.phys.EntityHitResult;
//import net.minecraft.world.phys.HitResult;
//import net.minecraft.world.phys.Vec3;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//import org.jetbrains.annotations.NotNull;
//
//import javax.annotation.Nullable;
//
//public class IcicleEntity extends AbstractArrow {
//    private float breakChance = 0.5f;
//    protected boolean dealtDamage;
//    private final static SoundEvent FALL_SOUND = SoundEvents.GLASS_FALL;
//    private static final ItemStack DEFAULT_ARROW_STACK = new ItemStack(FItems.ICICLE.get());
//
//
//    public IcicleEntity(EntityType<? extends AbstractArrow> entityType, Level level, ItemStack itemStack) {
//        super(entityType, level, itemStack);
//        setSoundEvent(FALL_SOUND);
//    }
//
//    public IcicleEntity(EntityType<? extends AbstractArrow> entityType, double x, double y, double z, Level level, ItemStack itemStack) {
//        super(entityType, x, y, z, level, itemStack);
//        setSoundEvent(FALL_SOUND);
//    }
//
//    public IcicleEntity(EntityType<? extends AbstractArrow> entityType, LivingEntity entity, Level level, ItemStack itemStack) {
//        super(entityType, entity, level, itemStack);
//        setSoundEvent(FALL_SOUND);
//    }
//
//    public IcicleEntity(EntityType<IcicleEntity> icicleEntityEntityType, Level level) {
//        this(icicleEntityEntityType, level, DEFAULT_ARROW_STACK);
//    }
//
//
//    @Nullable
//    protected EntityHitResult findHitEntity(Vec3 startPos, Vec3 endPos) {
//        return this.dealtDamage ? null : super.findHitEntity(startPos, endPos);
//    }
//
//
//    @OnlyIn(Dist.CLIENT)
//    public boolean shouldRender(double x, double y, double z) {
//        return true;
//    }
//
//    @Override
//    public void tick() {
//        if (inGroundTime > 4) {
//            dealtDamage = true;
//        }
//
//        super.tick();
//    }
//
//    @Override
//    protected void onHit(@NotNull HitResult rayTraceResult) {
//        super.onHit(rayTraceResult);
//
//        if (!this.level().isClientSide) {
//            if (random.nextFloat() < breakChance) {
//                level().broadcastEntityEvent(this, (byte) 3);
//                destroy();
//            }
//        }
//    }
//
//
//    @Override
//    protected void onHitEntity(EntityHitResult target) {
//        super.onHitEntity(target);
//        Entity entity = target.getEntity();
//        float damage = 3.0F;
//
//        Entity owner = getOwner();
//        DamageSource damagesource = damageSources().trident(this, (owner == null ? this : owner));
//        this.dealtDamage = true;
//
//        if (entity.hurt(damagesource, damage)) {
//
//            if (entity.getType() == EntityType.ENDERMAN) {
//                return;
//            }
//
//            if (entity instanceof LivingEntity) {
//                LivingEntity livingentity1 = (LivingEntity)entity;
//                if (owner instanceof LivingEntity) {
//                    EnchantmentHelper.doPostHurtEffects(livingentity1, owner);
//                    EnchantmentHelper.doPostDamageEffects((LivingEntity)owner, livingentity1);
//                }
//
//                this.doPostHurtEffects(livingentity1);
//            }
//        }
//
//        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
//        this.playSound(SoundEvents.GLASS_HIT, 1f, 1f);
//    }
//
//
//    public void playerTouch(Player player) {
//        Entity entity = getOwner();
//        if (entity == null || entity.getUUID() == player.getUUID()) {
//            super.playerTouch(player);
//        }
//    }
//
//
//    protected void destroy() {
//        playSound(SoundEvents.GLASS_BREAK, 1f, 1f);
//        this.remove(RemovalReason.DISCARDED);
//    }
//
//
//    @Override
//    protected SoundEvent getDefaultHitGroundSoundEvent() {
//        return SoundEvents.GLASS_BREAK;
//    }
//
//    @Override
//    protected ItemStack getPickupItem() {
//        return FItems.ICICLE.get().getDefaultInstance();
//    }
//
//    @Override
//    protected float getWaterInertia() {
//        return 0.99F;
//    }
//
////    @Override
////    public IPacket<?> getAddEntityPacket() {
////        return NetworkHooks.getEntitySpawningPacket(this);
////    }
//}
