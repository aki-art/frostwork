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

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(ALPINE_TUNDRA, alpineTundra(context));
        context.register(FROZEN_CAVE, frozenCave(context));
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
                        .waterColor(0x4159204)
                        .waterFogColor(0x329011)
                        .skyColor(0x10278388)
                        .grassColorOverride(0x13350267)
                        .foliageColorOverride(0x3962923)
                        .fogColor(0x9543081)

                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        //.backgroundMusic(Musics.createGameMusic(ModSounds.BAR_BRAWL.getHolder().get()))
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
                        .waterColor(0x4159204)
                        .waterFogColor(0x329011)
                        .skyColor(0x10278388)
                        .grassColorOverride(0x13350267)
                        .foliageColorOverride(0x3962923)
                        .fogColor(0x9543081)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        //.backgroundMusic(Musics.createGameMusic(ModSounds.BAR_BRAWL.getHolder().get()))
                        .build())
                .build();
    }

}
