package io.github.akiart.frostwork.client.world;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

// todo - this seems to have changed significantly since 1.16
@OnlyIn(Dist.CLIENT)
public class FantasiaDimensionRenderInfo extends DimensionSpecialEffects {
    public static final float CLOUD_HEIGHT = 213f;

    public FantasiaDimensionRenderInfo() {
        super(CLOUD_HEIGHT, true, SkyType.NONE, false, false);
    }

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float dayLight) {
        dayLight = Math.max(0.4f, dayLight);
        return fogColor.multiply((dayLight * 0.94F + 0.06F), dayLight * 0.94F + 0.06F, dayLight * 0.91F + 0.09F);
    }

    @Override
    public boolean isFoggyAt(int x, int z) {
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return super.renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
    }
}