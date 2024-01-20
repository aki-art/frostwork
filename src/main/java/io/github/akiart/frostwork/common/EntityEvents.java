package io.github.akiart.frostwork.common;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.effects.FEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

//@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
@Mod.EventBusSubscriber(modid = Frostwork.MOD_ID)
public class EntityEvents {
    @SubscribeEvent
    public static void onEntityActuallyHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        applyFrailDamage(event, entity);
    }

    private static void applyFrailDamage(LivingHurtEvent event, LivingEntity entity) {
        if(entity.hasEffect(FEffects.FRAIL.get())) {
            event.setAmount(event.getAmount() * 2f);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onMobEffectApplicable(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effectInstance = event.getEffectInstance();

        checkPoisonResistance(event, entity, effectInstance);
    }

    private static void checkPoisonResistance(MobEffectEvent.Applicable event, LivingEntity entity, MobEffectInstance effectInstance) {
        boolean hasImmunity = entity.hasEffect(FEffects.POISON_RESISTANCE.get());

        if(effectInstance.getEffect() == MobEffects.POISON && hasImmunity)
            event.setResult(Event.Result.DENY);
    }
}
