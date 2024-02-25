package io.github.akiart.frostwork.common.worldgen.features;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.blockTypes.FlippableSaplingBlock;
import io.github.akiart.frostwork.common.worldgen.FOrePlacement;
import io.github.akiart.frostwork.common.worldgen.features.configuredFeatures.VerdantGladeFeatures;
import io.github.akiart.frostwork.common.worldgen.features.placedFeatures.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class FPlacedFeatures {


    public static class Ores {
        public static final ResourceKey<PlacedFeature> EDELSTONE_COAL_ORE = key("edelstone_coal_ore");
        public static final ResourceKey<PlacedFeature> MARLSTONE_COAL_ORE = key("marlstone_coal_ore");
        public static final ResourceKey<PlacedFeature> MARLSTONE_WOLFRAMITE_ORE = key("marlstone_wolframite_ore");
        public static final ResourceKey<PlacedFeature> MARLSTONE_BURIED_OBJECT = key("marlstone_buried_object");
    }

    public static class Vegetation {
        public static final ResourceKey<PlacedFeature> SPORADIC_CANDELOPUE = key("sporadic_candeloupe");

        public static final ResourceKey<PlacedFeature> THIN_SHORT_FLOOR_VELWOOD_TREES = key("thin_short_velwood_trees");
        public static final ResourceKey<PlacedFeature> THIN_SHORT_CEILING_VELWOOD_TREES = key("thin_short_ceiling_velwood_trees");
        public static final ResourceKey<PlacedFeature> THIN_TALL_FLOOR_VELWOOD_TREES = key("thin_tall_velwood_trees");
        public static final ResourceKey<PlacedFeature> BIG_VELWOOD_TREES = key("big_velwood_trees");
        public static final ResourceKey<PlacedFeature> FROZEN_FOREST_ELM_TREES = key("frozen_forest_elm_trees");
    }

    public static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Frostwork.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        VerdantGladePlacements.register(context, configuredFeatures);
        AlpineTundraPlacements.register(context, configuredFeatures);
        SkySeaPlacements.register(context, configuredFeatures);
        GrimcapPlacements.register(context, configuredFeatures);
        SulfurSpringsPlacements.register(context, configuredFeatures);

        PlacementUtils.register(
                context,
                Vegetation.FROZEN_FOREST_ELM_TREES,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Vegetation.Trees.FROZEN_ELM),
                PlacementUtils.countExtra(2, 0.1F, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.THIN_SHORT_FLOOR_VELWOOD_TREES,
                configuredFeatures.getOrThrow(VerdantGladeFeatures.Vegetation.VELWOOD_SINGLE_SHORT),
                CountOnEveryLayerPlacement.of(25),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                PlacementUtils.isEmpty(),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(FBlocks.VELWOOD.sapling.get().defaultBlockState().setValue(FlippableSaplingBlock.VERTICAL_DIRECTION, Direction.DOWN), BlockPos.ZERO)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.THIN_SHORT_CEILING_VELWOOD_TREES,
                configuredFeatures.getOrThrow(VerdantGladeFeatures.Vegetation.VELWOOD_SINGLE_SHORT),
                CountOnEveryLayerPlacement.of(50),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                PlacementUtils.isEmpty(),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(FBlocks.VELWOOD.sapling.get().defaultBlockState().setValue(FlippableSaplingBlock.VERTICAL_DIRECTION, Direction.UP), BlockPos.ZERO)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.THIN_TALL_FLOOR_VELWOOD_TREES,
                configuredFeatures.getOrThrow(VerdantGladeFeatures.Vegetation.VELWOOD_SINGLE_TALL),
                CountOnEveryLayerPlacement.of(10),
                InSquarePlacement.spread(),
                PlacementUtils.isEmpty(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(FBlocks.VELWOOD.sapling.get().defaultBlockState().setValue(FlippableSaplingBlock.VERTICAL_DIRECTION, Direction.UP), BlockPos.ZERO)),

                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.BIG_VELWOOD_TREES,
                configuredFeatures.getOrThrow(VerdantGladeFeatures.Vegetation.VELWOOD_DOUBLE),
                CountOnEveryLayerPlacement.of(20),
                InSquarePlacement.spread(),
                PlacementUtils.isEmpty(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                //PlacementUtils.filteredByBlockSurvival(FBlocks.VELWOOD.sapling.get()),
                BiomeFilter.biome()
        );



        PlacementUtils.register(
                context,
                Vegetation.SPORADIC_CANDELOPUE,
                configuredFeatures.getOrThrow(VerdantGladeFeatures.Vegetation.CANDELOPUE),
                CountPlacement.of(21),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                PlacementUtils.filteredByBlockSurvival(FBlocks.ATTACHED_CANDELOUPE_STEM.get()),
                BiomeFilter.biome()
        );



        // coals
        registerCommonOre(context, configuredFeatures, Ores.EDELSTONE_COAL_ORE, FConfiguredFeatures.EDELSTONE_COAL_ORE, 30, 0, 300);
        registerCommonOre(context, configuredFeatures, Ores.MARLSTONE_COAL_ORE, FConfiguredFeatures.MARLSTONE_COAL_ORE, 30, 0, 300);

        // wolframite
        registerCommonOre(context, configuredFeatures, Ores.MARLSTONE_WOLFRAMITE_ORE, FConfiguredFeatures.MARLSTONE_WOLFRAMITE_ORE, 7, 10, 300);

        // buried objects
        registerCommonOre(context, configuredFeatures, Ores.MARLSTONE_BURIED_OBJECT, FConfiguredFeatures.MARLSTONE_BURIED_OBJECT, 4, 100, 300);
    }

    private static void registerCommonOre(BootstapContext<PlacedFeature> context, HolderGetter<ConfiguredFeature<?, ?>> features, ResourceKey<PlacedFeature> key, ResourceKey<ConfiguredFeature<?, ?>> feature, int count, int fromY, int toY) {
        register(
                context,
                key,
                features.getOrThrow(feature),
                FOrePlacement.commonOrePlacement(count, HeightRangePlacement.uniform(VerticalAnchor.absolute(fromY), VerticalAnchor.absolute(toY))));
    }

    public static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
