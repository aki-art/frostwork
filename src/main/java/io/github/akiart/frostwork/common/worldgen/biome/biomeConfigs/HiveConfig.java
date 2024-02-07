package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.worldgen.FCarvers;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class HiveConfig extends BaseBiomeConfig {

    public HiveConfig(BootstapContext<Biome> context) {
        super(context);
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        biomeBuilder
                .addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0f)
                .temperature(0.4f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xb5b292) // todo
                        .waterFogColor(0xb5b292)
                        .skyColor(0xf8c75d)
                        .fogColor(0xb3a25d)

                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        //.backgroundMusic(Musics.createGameMusic(ModSounds.BAR_BRAWL.getHolder().get()))
                        .build())
                .build();
    }
}
