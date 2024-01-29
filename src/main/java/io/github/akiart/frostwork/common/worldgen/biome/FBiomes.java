package io.github.akiart.frostwork.common.worldgen.biome;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.FCarvers;
import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class FBiomes {
    public static class Surface {
        public static final ResourceKey<Biome> ALPINE_TUNDRA = key("alpine_tundra");

    }

    public static class Cave {
        public static final ResourceKey<Biome> FROZEN_CAVE = key("frozen_cave");
        public static final ResourceKey<Biome> VERDANT_GLADE = key("verdant_glade");
        public static final ResourceKey<Biome> HIVE = key("hive");
        public static final ResourceKey<Biome> GRIMCAP_GROVE = key("grimcap_grove");
    }

    public static class Debug {
        public static final ResourceKey<Biome> DEBUG_RED = key("debug_red");
        public static final ResourceKey<Biome> DEBUG_BLUE = key("debug_blue");
    }

    public static class Sky {
        public static final ResourceKey<Biome> FLOATING_MOUNTAINS = key("floating_mountains");
    }

    public static class Special {

    }

    private static ResourceKey<Biome> key(String name) {
            return ResourceKey.create(Registries.BIOME, new ResourceLocation(Frostwork.MOD_ID, name));
    }

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(Surface.ALPINE_TUNDRA, alpineTundra(context));
        context.register(Cave.FROZEN_CAVE, frozenCave(context));
        context.register(Cave.VERDANT_GLADE, verdantGlade(context));
        context.register(Cave.GRIMCAP_GROVE, grimcapGrove(context));
        context.register(Cave.HIVE, hive(context));
        context.register(Debug.DEBUG_RED, debugBiome(context, 0xFF0000));
        context.register(Debug.DEBUG_BLUE, debugBiome(context, 0x000000FF));
        context.register(Sky.FLOATING_MOUNTAINS, debugBiome(context, 0x10278388));
    }

    private static Biome hive(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        biomeBuilder.addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        // biomeBuilder .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FPlacedFeatures.EDELSTONE_COAL_ORE_PLACED);

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

    private static Biome grimcapGrove(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        biomeBuilder.addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.GROVE_BULBSACKS);

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
                        .fogColor(0x823622)

                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES))
                        .build())
                .build();
    }

    private static Biome verdantGlade(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        biomeBuilder.addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.SPORADIC_CANDELOPUE);

       // biomeBuilder .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FPlacedFeatures.EDELSTONE_COAL_ORE_PLACED);

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


    private static Biome frozenCave(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        biomeBuilder.addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        biomeBuilder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FPlacedFeatures.EDELSTONE_COAL_ORE_PLACED);

        //BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0f)
                .temperature(-0.8f)
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

    private static Biome debugBiome(BootstapContext<Biome> context, int color) {
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

    public static Biome alpineTundra(BootstapContext<Biome> context) {

        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 5, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 1, 4));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        biomeBuilder.addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        BiomeDefaultFeatures.addFerns(biomeBuilder);
        biomeBuilder
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FPlacedFeatures.Surface.SMALL_DIORITE_BOULDERS)
                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FPlacedFeatures.Surface.LARGE_DIORITE_BOULDERS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.TUNDRA_FLOWERS)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.BEARBERRY_PATCH);

        //biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.SPORADIC_CANDELOPUE);
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
