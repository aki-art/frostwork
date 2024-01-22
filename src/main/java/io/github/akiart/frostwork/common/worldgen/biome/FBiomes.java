package io.github.akiart.frostwork.common.worldgen.biome;

import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;

public class FBiomes {
    public static final ResourceKey<Biome> ALPINE_TUNDRA = ResourceKey.create(Registries.BIOME, new ResourceLocation(Frostwork.MOD_ID, "alpine_tundra"));
    public static final ResourceKey<Biome> FROZEN_CAVE = ResourceKey.create(Registries.BIOME, new ResourceLocation(Frostwork.MOD_ID, "frozen_cave"));
    public static final ResourceKey<Biome> DEBUG_RED = ResourceKey.create(Registries.BIOME, new ResourceLocation(Frostwork.MOD_ID, "debug_red"));
    public static final ResourceKey<Biome> DEBUG_BLUE = ResourceKey.create(Registries.BIOME, new ResourceLocation(Frostwork.MOD_ID, "debug_blue"));
    public static final ResourceKey<Biome> FLOATING_MOUNTAINS = ResourceKey.create(Registries.BIOME, new ResourceLocation(Frostwork.MOD_ID, "floating_mountains"));

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(ALPINE_TUNDRA, alpineTundra(context));
        context.register(FROZEN_CAVE, frozenCave(context));
        context.register(DEBUG_RED, debugBiome(context, 0xFF0000));
        context.register(DEBUG_BLUE, debugBiome(context, 0x000000FF));
        context.register(FLOATING_MOUNTAINS, debugBiome(context, 0x10278388));
    }

    private static Biome frozenCave(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

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
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 5, 4, 4));

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        BiomeDefaultFeatures.addFerns(biomeBuilder);

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
