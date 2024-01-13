package io.github.akiart.frostwork.common.potion;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.effects.FEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Frostwork.MOD_ID);
    public static DeferredHolder<Potion, Potion> FRAIL = POTIONS.register("frail", () -> new Potion(new MobEffectInstance(FEffects.FRAIL.get(), 1800)));
    public static DeferredHolder<Potion, Potion> POISON_RESISTANCE = POTIONS.register("poison_resistance", () -> new Potion(new MobEffectInstance(FEffects.POISON_RESISTANCE.get(), 1800)));
}
