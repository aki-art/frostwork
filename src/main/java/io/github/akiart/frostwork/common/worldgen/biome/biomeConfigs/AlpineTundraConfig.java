package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.worldgen.FCarvers;
import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class AlpineTundraConfig extends BaseBiomeConfig {

    public AlpineTundraConfig(BootstapContext<Biome> context) {
        super(context);
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        spawnBuilder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 5, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 1, 4));

        biomeBuilder
                .addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        BiomeDefaultFeatures.addFerns(biomeBuilder);
        biomeBuilder
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FPlacedFeatures.Surface.SMALL_DIORITE_BOULDERS)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FPlacedFeatures.Surface.LARGE_DIORITE_BOULDERS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.TUNDRA_FLOWERS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.BEARBERRY_PATCH);

        addCommonSurfaceFeatures(biomeBuilder);

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
}
