package io.github.akiart.frostwork.common.worldgen.features.placedFeatures;

import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import io.github.akiart.frostwork.common.worldgen.features.configuredFeatures.GrimcapFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class GrimcapPlacements {
    public static final ResourceKey<PlacedFeature> GROVE_BULBSACKS = FPlacedFeatures.key("grove_bulbsacks");
    public static final ResourceKey<PlacedFeature> GROVE_MEDIUM_RED_GRIMCAP = FPlacedFeatures.key("grove_medium_red_grimcap");
    public static final ResourceKey<PlacedFeature> GROVE_LARGE_GRIMCAP = FPlacedFeatures.key("grove_large_grimcap");
    public static final ResourceKey<PlacedFeature> GROVE_GIANT_GRIMCAP = FPlacedFeatures.key("grove_giant_grimcap");
    public static final ResourceKey<PlacedFeature> GROVE_MEDIUM_PURPLE_GRIMCAP = FPlacedFeatures.key("grove_medium_purple_grimcap");
    public static final ResourceKey<PlacedFeature> GROVE_MILDEW_FUZZ = FPlacedFeatures.key("grove_mildew_fuzz");
    public static final ResourceKey<PlacedFeature> GRIMCAP_ACID = FPlacedFeatures.key("grimcap_acid_deltas");
    public static final ResourceKey<PlacedFeature> SANGUITE_PILLARS = FPlacedFeatures.key("sanguite_pillars");

    public static void register(BootstapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {
        PlacementUtils.register(
                context,
                SANGUITE_PILLARS,
                configuredFeatures.getOrThrow(GrimcapFeatures.SANGUITE_PILLAR),
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                PlacementUtils.isEmpty(),
                //EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),

                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                GROVE_BULBSACKS,
                configuredFeatures.getOrThrow(GrimcapFeatures.GRIMCAP_BULBSACK),
                //RarityFilter.onAverageOnceEvery(7),
                //new RidgedCountPlacement(4f, 0.7f, 999f),
                //InSquarePlacement.spread(),
                //PlacementUtils.FULL_RANGE,
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                GROVE_MILDEW_FUZZ,
                configuredFeatures.getOrThrow(GrimcapFeatures.MILDEW_FUZZ),
                NoiseBasedCountPlacement.of(120, 80.0, 0.0),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                GROVE_MEDIUM_RED_GRIMCAP,
                configuredFeatures.getOrThrow(GrimcapFeatures.MEDIUM_GRIMCAP),
                CountPlacement.of(64),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                GROVE_LARGE_GRIMCAP,
                configuredFeatures.getOrThrow(GrimcapFeatures.LARGE_GRIMCAP),
                CountPlacement.of(64),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                GROVE_GIANT_GRIMCAP,
                configuredFeatures.getOrThrow(GrimcapFeatures.GIANT_GRIMCAP),
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                GRIMCAP_ACID,
                configuredFeatures.getOrThrow(GrimcapFeatures.SANGUITE_ACID_DELTAS),
                CountOnEveryLayerPlacement.of(40),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        );
    }
}
