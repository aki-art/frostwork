package io.github.akiart.frostwork.common.worldgen.features.placedFeatures;

import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import io.github.akiart.frostwork.common.worldgen.features.configuredFeatures.AlpineTundraFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class AlpineTundraPlacements {


    public static final ResourceKey<PlacedFeature> TUNDRA_FLOWERS = FPlacedFeatures.key("tundra_flowers");
    public static final ResourceKey<PlacedFeature> BEARBERRY_PATCH = FPlacedFeatures.key("bearberry_patch");
    public static final ResourceKey<PlacedFeature> SMALL_DIORITE_BOULDERS = FPlacedFeatures.key("small_diorite_boulders");
    public static final ResourceKey<PlacedFeature> LARGE_DIORITE_BOULDERS = FPlacedFeatures.key("large_diorite_boulders");

    public static void register(BootstapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {

        PlacementUtils.register(
                context,
                TUNDRA_FLOWERS,
                configuredFeatures.getOrThrow(AlpineTundraFeatures.TUNDRA_FLOWERS),
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                BEARBERRY_PATCH,
                configuredFeatures.getOrThrow(AlpineTundraFeatures.TUNDRA_BEARBERRY),
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                SMALL_DIORITE_BOULDERS,
                configuredFeatures.getOrThrow(AlpineTundraFeatures.DIORITE_BOULDERS),
                RarityFilter.onAverageOnceEvery(10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                LARGE_DIORITE_BOULDERS,
                configuredFeatures.getOrThrow(AlpineTundraFeatures.LARGE_DIORITE_BOULDERS),
                RarityFilter.onAverageOnceEvery(60),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
    }
}
