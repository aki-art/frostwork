package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import io.github.akiart.frostwork.common.worldgen.features.placedFeatures.SkySeaPlacements;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SkySeaConfig extends BaseBiomeConfig {
    public SkySeaConfig(BootstapContext<Biome> context) {
        super(context, false);
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        biomeBuilder
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, SkySeaPlacements.PONDS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, SkySeaPlacements.CEILING_STARS)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, SkySeaPlacements.CEILING_LIGHTS);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0f)
                .temperature(0.4f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x2d72d8)
                        .waterFogColor(0x2d72d8)
                        .skyColor(0x4d596f)
                        .grassColorOverride(0x1f6371)
                        .foliageColorOverride(0x206864)
                        .fogColor(0x3c7dde)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES))
                        .ambientParticle(new AmbientParticleSettings(ParticleTypes.RAIN, 0.025f)) // todo: switch this to a plant like the flowers in lush caves
                        .build())
                .build();
    }

    public static SurfaceRules.RuleSource createSurfaceRules() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(FBiomes.Cave.DOWNPOUR),
                        state(FBlocks.SOAPSTONE.block.get()))
        );
    }
}
