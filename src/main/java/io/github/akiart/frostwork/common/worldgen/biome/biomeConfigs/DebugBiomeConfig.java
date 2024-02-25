package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.*;

public class DebugBiomeConfig extends BaseBiomeConfig{
    private final int color;

    public DebugBiomeConfig(BootstapContext<Biome> context, int color) {
        super(context, false);
        this.color = color;
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0f)
                .temperature(0.5f)
                .generationSettings(BiomeGenerationSettings.EMPTY)
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(color)
                        .waterFogColor(color)
                        .skyColor(color)
                        .fogColor(color)

                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }
}
