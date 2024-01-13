package io.github.akiart.frostwork.common;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.effects.FEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;

//@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
@Mod.EventBusSubscriber(modid = Frostwork.MOD_ID)
public class EntityEvents {
    @SubscribeEvent
    public static void onEntityActuallyHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity.hasEffect(FEffects.FRAIL.get())) {
            event.setAmount(event.getAmount() * 2f);
        }
    }
}
