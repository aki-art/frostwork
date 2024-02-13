package io.github.akiart.frostwork.client.particles;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.client.particles.particleOptions.VelwoodInfestationParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Frostwork.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ACID_BUBBLE = PARTICLES.register("acid_bubble", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRIM_SPORE = PARTICLES.register("grimcap_spore", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, VelwoodInfestationParticleOptions> VELMITE_INFECTION = PARTICLES.register("velmite_infection", () -> new VelwoodInfestationParticleOptions(Vec3.ZERO));
}
