package io.github.akiart.frostwork.client;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.FDimensionTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ViewportEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FogRenderTweaks {

    private static boolean isInLiquid(Camera camera) {
        FluidState state = camera.getEntity().level().getFluidState(camera.getBlockPosition());
        return (camera.getPosition().y < (double) ((float) camera.getBlockPosition().getY() + state.getHeight(camera.getEntity().level(), camera.getBlockPosition())));
    }
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onFogRender(ViewportEvent.RenderFog event) {

        if(Frostwork.DEBUG_DISABLE_FOG)
            return;

        if(event.getMode() != FogRenderer.FogMode.FOG_TERRAIN)
            return;

        var level = Minecraft.getInstance().level;


        if(level == null || level.dimensionTypeId() != FDimensionTypes.FANTASIA_TYPE || isInLiquid(event.getCamera()))
            return;

        var y = event.getCamera().getPosition().y;

        if(y > 220)
            return;

        var nearPlane = 20f;
        var farPlane = 250f;

        if(y >= 100) {
            var fogginess = (float)Mth.clamp(((y - 100) / 120f), 0f, 1f);
            farPlane = Mth.lerp(fogginess, farPlane, 1000f);
            nearPlane = Mth.lerp(fogginess, 20, Math.max(20, event.getNearPlaneDistance()));
        }

        event.setNearPlaneDistance(nearPlane);
        event.setFarPlaneDistance(farPlane);

        event.setCanceled(true);
    }

}
