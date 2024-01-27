package io.github.akiart.frostwork.common.capability.acidVulnerable;

import io.github.akiart.frostwork.common.FTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

public class AcidVulnerable implements IAcidVulnerable, ICapabilityProvider, INBTSerializable<CompoundTag> {
    private boolean isInAcid;
    private boolean wasInAcid;
    private boolean wasEyeInAcid;
    private Entity entity;
    private static double MIN_ACID_LEVEL = 0.9D;
    private static double ACID_MOTION_SCALE = 0.014D;

    private AcidVulnerable instance = null;
    private final Lazy<AcidVulnerable> optional = Lazy.of(this::create);

    private AcidVulnerable create() {
        if(instance == null)
            this.instance = new AcidVulnerable();

        return this.instance;
    }

    @Override
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void tick() {
        if(isInAcid) {
            entity.updateFluidHeightAndDoFluidPushing(FTags.Fluids.ACID, ACID_MOTION_SCALE);

            double fluidHeight = entity.getFluidHeight(FTags.Fluids.ACID);

            if(fluidHeight < 0.005f){
                isInAcid = false;
                return;
            }

            if(!wasInAcid && entity.tickCount > 0) {
                doAcidSplashEffect(entity);
            }

            wasInAcid = true;
        }
        else if(wasInAcid) {
            // update to clear
            entity.updateFluidHeightAndDoFluidPushing(FTags.Fluids.ACID, ACID_MOTION_SCALE);
            entity.setSwimming(false);
            isInAcid = false;
            wasInAcid = false;
        }
    }

    // largely a copy paste of Entity.doWaterSplashEffect()
    // spawns particles and plays sounds for swimming
    protected void doAcidSplashEffect(Entity entity) {
        Entity controllingEntity = entity.isVehicle() && entity.getControllingPassenger() != null ? entity.getControllingPassenger() : entity;
        float f = controllingEntity == entity ? 0.2F : 0.9F;
        Vec3 vector3d = controllingEntity.getDeltaMovement();
        float speed = Mth.sqrt((float)(vector3d.x * vector3d.x * (double) 0.2F + vector3d.y * vector3d.y + vector3d.z * vector3d.z * (double) 0.2F) * f);

        speed = Math.min(speed, 1);

        entity.playSound(SoundEvents.GENERIC_SPLASH, speed, 1.0F + (entity.random.nextFloat() - entity.random.nextFloat()) * 0.4F);

        float y = (float) Mth.floor(entity.getY());

        EntityDimensions dimensions = entity.getType().getDimensions();

        for (int i = 0; (float) i < 1.0F + dimensions.width * 20.0F; ++i) {
            double x = entity.getRandomX(dimensions.width);
            double z = entity.getRandomZ(dimensions.width);
            entity.level().addParticle(ParticleTypes.BUBBLE, entity.getX() + x, y + 1.0F, entity.getZ() + z, vector3d.x, vector3d.y - entity.random.nextDouble() * (double) 0.2F, vector3d.z);
        }

        for (int i = 0; (float) i < 1.0F + dimensions.width * 20.0F; ++i) {
            double x = entity.getRandomX(dimensions.width);
            double z = entity.getRandomZ(dimensions.width);
            entity.level().addParticle(ParticleTypes.SPLASH, entity.getX() + x, y + 1.0F, entity.getZ() + z, vector3d.x, vector3d.y, vector3d.z);
        }
    }

    @Override
    public boolean isInAcid() {
        return isInAcid;
    }

    @Override
    public boolean wasInAcid() {
        return wasInAcid;
    }

    @Override
    public void setIsInAcid(boolean value) {
        isInAcid = value;
    }

    @Override
    public boolean wasEyeInAcid() {
        return wasEyeInAcid;
    }

    @Override
    public void setEyeWasInAcid(boolean value) {
        wasEyeInAcid = value;
    }

    @Override
    public void setWasInAcid(boolean value) {
        wasInAcid = value;
    }

    void initState(boolean isInAcid, boolean wasInAcid) {
        this.isInAcid = isInAcid;
        this.wasInAcid = wasInAcid;
    }

    @Nullable
    @Override
    public Object getCapability(Object object, Object context) {
        return null;
    }

    @Override
    public CompoundTag serializeNBT() {
        var tag = new CompoundTag();
        tag.putBoolean("isInAcid", isInAcid());
        tag.putBoolean("wasInAcid", wasInAcid());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        isInAcid = nbt.getBoolean("isInAcid");
        wasInAcid = nbt.getBoolean("wasInAcid");
    }
}
