package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.client.particles.FParticles;
import io.github.akiart.frostwork.common.worldgen.features.placedFeatures.GrimcapPlacements;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class GrimcapGroveConfig extends BaseBiomeConfig {

    public GrimcapGroveConfig(BootstapContext<Biome> context) {
        super(context, false);
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        biomeBuilder
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GrimcapPlacements.GROVE_BULBSACKS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GrimcapPlacements.GROVE_MEDIUM_RED_GRIMCAP)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GrimcapPlacements.GROVE_LARGE_GRIMCAP)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, GrimcapPlacements.GROVE_MILDEW_FUZZ)
                .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, GrimcapPlacements.GRIMCAP_ACID)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, GrimcapPlacements.SANGUITE_PILLARS)
                .addFeature(GenerationStep.Decoration.RAW_GENERATION, GrimcapPlacements.GROVE_GIANT_GRIMCAP);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0f)
                .temperature(0.4f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x5ea0b1)
                        .waterFogColor(0x5ea0b1)
                        .skyColor(0x6f2914)
                        .grassColorOverride(0x6d2c1b)
                        .foliageColorOverride(0x6d2c1b)
                        .fogColor(0xc4da2f)//6bbd3e)//823622)//0x589db0
                        .ambientParticle(new AmbientParticleSettings(FParticles.GRIM_SPORE.get(), 0.025F))

                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES))
                        .build())
                .build();
    }
}
