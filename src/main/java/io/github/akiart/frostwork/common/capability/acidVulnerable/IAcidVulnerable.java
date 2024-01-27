package io.github.akiart.frostwork.common.capability.acidVulnerable;

import net.minecraft.world.entity.Entity;

public interface IAcidVulnerable {
    boolean wasInAcid();
    void setWasInAcid(boolean value);
    boolean isInAcid();
    void setEntity(Entity entity);
    void tick();
    void setIsInAcid(boolean value);
    boolean wasEyeInAcid();
    void setEyeWasInAcid(boolean value);
    default boolean isUnderAcid() {
        return wasEyeInAcid() && isInAcid();
    }
}
