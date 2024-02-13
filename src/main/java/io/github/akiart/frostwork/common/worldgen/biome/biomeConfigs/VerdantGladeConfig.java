package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.worldgen.FCarvers;
import io.github.akiart.frostwork.common.worldgen.FNoises;
import io.github.akiart.frostwork.common.worldgen.FSurfaceRules;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class VerdantGladeConfig extends BaseBiomeConfig {
    public VerdantGladeConfig(BootstapContext<Biome> context) {
        super(context);
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        biomeBuilder
                .addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        biomeBuilder
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.SPORADIC_CANDELOPUE)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.THIN_TALL_FLOOR_VELWOOD_TREES)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.THIN_SHORT_FLOOR_VELWOOD_TREES)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.BIG_VELWOOD_TREES);


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
                        .grassColorOverride(0x559945)
                        .foliageColorOverride(0x3f8b3e)
                        .fogColor(0x4aadc3)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)

                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES))
                        .build())
                .build();
    }

    public static SurfaceRules.RuleSource createSurfaceRules()
    {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(FBiomes.Cave.VERDANT_GLADE),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.ON_CEILING,
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition(FNoises.ALPINE_TUNDRA_SURFACE, -0.8, 3),
                                                        FSurfaceRules.MOSS),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition(FNoises.ALPINE_TUNDRA_SURFACE, 3, 4),
                                                        FSurfaceRules.MUD
                                                )
                                        )),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.ON_FLOOR,
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition(FNoises.ALPINE_TUNDRA_SURFACE, -0.8, 0.1),
                                                        FSurfaceRules.MOSS),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition(FNoises.ALPINE_TUNDRA_SURFACE, 0.1, 3),
                                                        FSurfaceRules.MUD
                                                ),
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition(FNoises.ALPINE_TUNDRA_SURFACE, 3),
                                                        FSurfaceRules.GRASS
                                                )
                                        )),
                                FSurfaceRules.VERDANT_ROCK)
                )
        );
    }
}
