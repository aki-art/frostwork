package io.github.akiart.frostwork.client.particles;

import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Frostwork.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ACID_BUBBLE = PARTICLES.register("acid_bubble", () -> new SimpleParticleType(true));
}
