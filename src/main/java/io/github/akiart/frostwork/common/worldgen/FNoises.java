package io.github.akiart.frostwork.common.worldgen;

import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class FNoises {
    public static ResourceKey<NormalNoise.NoiseParameters> ALPINE_TUNDRA_SURFACE = createKey("alpine_tundra_surface");
    public static ResourceKey<NormalNoise.NoiseParameters> FROZEN_CAVERN_ICE = createKey("frozen_cavern_ice");

    private static ResourceKey<NormalNoise.NoiseParameters> createKey(String key) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(Frostwork.MOD_ID, key));
    }

    public static void bootstrap(BootstapContext<NormalNoise.NoiseParameters> context) {
        context.register(ALPINE_TUNDRA_SURFACE, new NormalNoise.NoiseParameters(-4, 10, 10));
        context.register(FROZEN_CAVERN_ICE, new NormalNoise.NoiseParameters(-4, 10, 20, 30));
    }
}
