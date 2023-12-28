package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.FBiomeModifiers;
import io.github.akiart.frostwork.common.worldgen.FConfiguredFeatures;
import io.github.akiart.frostwork.common.worldgen.FPlacedFeatures;
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
            .add(Registries.CONFIGURED_FEATURE, FConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, FPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, FBiomeModifiers::bootstrap);

    public FWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Frostwork.MOD_ID));
    }
}