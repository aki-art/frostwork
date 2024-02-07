package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.worldgen.FCarvers;
import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class VerdantGladeConfig extends BaseBiomeConfig {

    public VerdantGladeConfig(BootstapContext<Biome> context) {
        super(context);
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        biomeBuilder
                .addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        biomeBuilder
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.SPORADIC_CANDELOPUE);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0f)
                .temperature(0.4f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4159204) // todo
                        .waterFogColor(329011)
                        .skyColor(10278388)
                        .grassColorOverride(13350267)
                        .foliageColorOverride(3962923)
                        .fogColor(9543081)

                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES))
                        .build())
                .build();
    }
}
