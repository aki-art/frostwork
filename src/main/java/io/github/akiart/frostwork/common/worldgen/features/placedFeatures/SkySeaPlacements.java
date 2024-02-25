package io.github.akiart.frostwork.common.worldgen.features.placedFeatures;

import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import io.github.akiart.frostwork.common.worldgen.features.configuredFeatures.SkySeaFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class SkySeaPlacements {
    public static final ResourceKey<PlacedFeature> PONDS = FPlacedFeatures.key("downpour_ponds");
    public static final ResourceKey<PlacedFeature> CEILING_LIGHTS = FPlacedFeatures.key("ceiling_lights");
    public static final ResourceKey<PlacedFeature> CEILING_STARS = FPlacedFeatures.key("ceiling_stars");

    public static void register(BootstapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {

        PlacementUtils.register(
                context,
                PONDS,
                configuredFeatures.getOrThrow(SkySeaFeatures.SOAPSTONE_POND),
                CountPlacement.of(62),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome());

        PlacementUtils.register(
                context,
                CEILING_STARS,
                configuredFeatures.getOrThrow(SkySeaFeatures.STARS),
                CountPlacement.of(32),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome() );

        PlacementUtils.register(
                context,
                CEILING_LIGHTS,
                configuredFeatures.getOrThrow(SkySeaFeatures.CEILING_LIGHTS),
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(0)),
                BiomeFilter.biome() );
    }
}
