package io.github.akiart.frostwork.common.worldgen;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.features.FConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class FPlacedFeatures {
    public static final ResourceKey<PlacedFeature> EDELSTONE_COAL_ORE_PLACED = registerKey("edelstone_coal_ore_placed");

    public static class Surface {
        public static final ResourceKey<PlacedFeature> SMALL_DIORITE_BOULDERS = registerKey("small_diorite_boulders");
        public static final ResourceKey<PlacedFeature> LARGE_DIORITE_BOULDERS = registerKey("large_diorite_boulders");
    }

    public static class Vegetation {
        public static final ResourceKey<PlacedFeature> TUNDRA_FLOWERS = registerKey("tundra_flowers");
        public static final ResourceKey<PlacedFeature> BEARBERRY_PATCH = registerKey("bearberry_patch");
        public static final ResourceKey<PlacedFeature> SPORADIC_CANDELOPUE = registerKey("sporadic_candeloupe");
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Frostwork.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(
                context,
                Vegetation.TUNDRA_FLOWERS,
                configuredFeatures.getOrThrow(FConfiguredFeatures.TUNDRA_FLOWERS),
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );


        PlacementUtils.register(
                context,
                Vegetation.SPORADIC_CANDELOPUE,
                configuredFeatures.getOrThrow(FConfiguredFeatures.VERDANT_SINGLE_CANDELOUPE),
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(20),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.BEARBERRY_PATCH,
                configuredFeatures.getOrThrow(FConfiguredFeatures.TUNDRA_BEARBERRY),
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                //CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Surface.SMALL_DIORITE_BOULDERS,
                configuredFeatures.getOrThrow(FConfiguredFeatures.DIORITE_BOULDERS),
                RarityFilter.onAverageOnceEvery(10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Surface.LARGE_DIORITE_BOULDERS,
                configuredFeatures.getOrThrow(FConfiguredFeatures.LARGE_DIORITE_BOULDERS),
                RarityFilter.onAverageOnceEvery(60),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );

        register(
                context,
                EDELSTONE_COAL_ORE_PLACED,
                configuredFeatures.getOrThrow(FConfiguredFeatures.EDELSTONE_COAL_ORE),
                FOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
