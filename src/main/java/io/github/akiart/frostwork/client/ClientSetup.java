package io.github.akiart.frostwork.client;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.client.particles.FParticles;
import io.github.akiart.frostwork.client.particles.particleTypes.AcidBubbleParticle;
import io.github.akiart.frostwork.client.particles.particleTypes.AmbientSuspendedParticle;
import io.github.akiart.frostwork.client.particles.particleTypes.VelmiteInfectionParticle;
import io.github.akiart.frostwork.client.renderer.entityRenderer.WurmArrowRenderer;
import io.github.akiart.frostwork.client.world.FantasiaDimensionRenderInfo;
import io.github.akiart.frostwork.common.FEntityTypes;
import io.github.akiart.frostwork.common.fluid.FFluids;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = Frostwork.MOD_ID)
public class ClientSetup {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent evt) {
        ItemBlockRenderTypes.setRenderLayer(FFluids.ACID_SOURCE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(FFluids.ACID_FLOWING.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(FEntityTypes.WURM_ARROW.get(), WurmArrowRenderer::new);
    }

    @SubscribeEvent
    public static void registerDimEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(FantasiaDimensionRenderInfo.KEY, new FantasiaDimensionRenderInfo());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FParticles.GRIM_SPORE.get(), AmbientSuspendedParticle.GrimSporeProvider::new);
        event.registerSpriteSet(FParticles.ACID_BUBBLE.get(), AcidBubbleParticle.Provider::new);
        event.registerSpriteSet(FParticles.VELMITE_INFECTION.get(), VelmiteInfectionParticle.Provider::new);
        //mc.particleEngine.register(FParticleTypes.DRIPPING_ACID.get(), AcidDripParticle.DrippingAcidFactory::new);
        //mc.particleEngine.register(FParticleTypes.FALLING_ACID.get(), AcidDripParticle.FallingAcidFactory::new);
    }
}
