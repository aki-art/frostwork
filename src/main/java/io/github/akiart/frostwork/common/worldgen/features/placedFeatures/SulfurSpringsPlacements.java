package io.github.akiart.frostwork.common.worldgen.features.placedFeatures;

import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import io.github.akiart.frostwork.common.worldgen.features.configuredFeatures.SulfurSpringsFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class SulfurSpringsPlacements {
    public static final ResourceKey<PlacedFeature> PONDS = FPlacedFeatures.key("acid_ponds");
    public static void register(BootstapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures) {

        PlacementUtils.register(
                context,
                PONDS,
                configuredFeatures.getOrThrow(SulfurSpringsFeatures.SULFUR_POOL),
                CountPlacement.of(62),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome());
    }
}
