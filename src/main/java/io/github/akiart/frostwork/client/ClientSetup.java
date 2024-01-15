package io.github.akiart.frostwork.client;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.client.renderer.entityRenderer.WurmArrowRenderer;
import io.github.akiart.frostwork.common.FEntityTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = Frostwork.MOD_ID)
public class ClientSetup {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {

    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FEntityTypes.WURM_ARROW.get(), WurmArrowRenderer::new);
    }
}
