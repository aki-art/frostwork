package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSource;

public class FBiomeSources {

    public static final ResourceKey<Codec<? extends BiomeSource>> FANTASIA = ResourceKey.create(Registries.BIOME_SOURCE,
            new ResourceLocation(Frostwork.MOD_ID, "fantasia"));

    public static void boostrap(BootstapContext<Codec<? extends BiomeSource>> context) {
        context.register(FANTASIA, FantasiaBiomeSource.CODEC);
    }
}
