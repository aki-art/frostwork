package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.*;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class FWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, FDimensionTypes::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, FConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FPlacedFeatures::bootstrap)
            .add(Registries.BIOME, FBiomes::boostrap)
            //.add(Registries.BIOME_SOURCE, FBiomeSources::boostrap)
            .add(Registries.NOISE_SETTINGS, FNoiseGenerationSettings::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FBiomeModifiers::bootstrap)
            .add(Registries.LEVEL_STEM, FDimensions::bootstrap);

    public FWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Frostwork.MOD_ID));
    }
}