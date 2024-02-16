package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.*;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import io.github.akiart.frostwork.common.worldgen.features.FConfiguredFeatures;
import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class FWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.NOISE, FNoises::bootstrap)
            .add(Registries.CONFIGURED_CARVER, FCarvers::bootstrap)
            .add(Registries.DIMENSION_TYPE, FDimensionTypes::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, FConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FPlacedFeatures::bootstrap)
            .add(Registries.BIOME, FBiomes::boostrap)
            .add(Registries.NOISE_SETTINGS, FNoiseGenerationSettings::bootstrap)
            .add(Registries.LEVEL_STEM, FDimensions::bootstrap);

    public FWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Frostwork.MOD_ID));
    }
}
