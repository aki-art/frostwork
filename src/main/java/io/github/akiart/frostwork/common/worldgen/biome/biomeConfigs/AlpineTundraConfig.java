package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.worldgen.FNoises;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import io.github.akiart.frostwork.common.worldgen.features.placedFeatures.AlpineTundraPlacements;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class AlpineTundraConfig extends BaseBiomeConfig {

    public AlpineTundraConfig(BootstapContext<Biome> context) {
        super(context, true);
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        spawnBuilder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 5, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 1, 4));

        BiomeDefaultFeatures.addFerns(biomeBuilder);
        biomeBuilder
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, AlpineTundraPlacements.SMALL_DIORITE_BOULDERS)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, AlpineTundraPlacements.LARGE_DIORITE_BOULDERS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AlpineTundraPlacements.TUNDRA_FLOWERS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AlpineTundraPlacements.BEARBERRY_PATCH);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.1f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .skyColor(10278388)
                        .grassColorOverride(13350267)
                        .foliageColorOverride(3962923)
                        .fogColor(9543081)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        //.backgroundMusic(Musics.createGameMusic(ModSounds.BAR_BRAWL.getHolder().get()))
                        .build())
                .build();
    }

    public static SurfaceRules.RuleSource createSurfaceRules() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(FBiomes.Surface.ALPINE_TUNDRA),
                        //SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
                            SurfaceRules.sequence(
                                    // if on surface, grass or gravel
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.ON_FLOOR,
                                            SurfaceRules.sequence(
                                                    // if underwater, just place dirt
                                                    SurfaceRules.ifTrue(
                                                            SurfaceRules.waterBlockCheck(0, 0),
                                                                    SurfaceRules.sequence(
                                                                        // random gravel
                                                                        SurfaceRules.ifTrue(
                                                                                SurfaceRules.noiseCondition(FNoises.ALPINE_TUNDRA_SURFACE, -99, -0.5),
                                                                                GRAVEL
                                                                        ),
                                                                        // random dry grass
                                                                        SurfaceRules.ifTrue(
                                                                                SurfaceRules.noiseCondition(FNoises.ALPINE_TUNDRA_SURFACE, 0.15),
                                                                                DRY_GRASS
                                                                        ),
                                                                        // the rest is regular grass
                                                                        GRASS)
                                                                ),
                                                                DIRT
                                                            )
                                                    )
                                            )
                                    ),
                                    // below grass, place dirt
                                    SurfaceRules.ifTrue(
                                        SurfaceRules.UNDER_FLOOR,
                                        DIRT
                                    )
                            );
    }
}
