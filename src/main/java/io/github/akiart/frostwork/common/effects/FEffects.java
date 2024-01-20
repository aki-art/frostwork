package io.github.akiart.frostwork.common.effects;

import io.github.akiart.frostwork.Consts;
import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Frostwork.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> FRAIL = EFFECTS.register("frail",
            () -> new FrailEffect(MobEffectCategory.HARMFUL, Consts.COLORS.FRAIL_EFFECT)
                    .addAttributeModifier(Attributes.ARMOR, "b089f119-3f8b-4d36-b232-c2e5b2aef05d", -4, AttributeModifier.Operation.ADDITION));

    public static final DeferredHolder<MobEffect, MobEffect> POISON_RESISTANCE = EFFECTS.register("poison_resistance",
            () -> new PoisonResistanceEffect(MobEffectCategory.BENEFICIAL, Consts.COLORS.POISON_RESISTANCE));

}
