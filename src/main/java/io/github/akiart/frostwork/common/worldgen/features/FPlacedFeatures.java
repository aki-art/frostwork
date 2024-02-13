package io.github.akiart.frostwork.common.worldgen.features;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.FOrePlacement;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
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

    public static class Surface {
        public static final ResourceKey<PlacedFeature> SMALL_DIORITE_BOULDERS = key("small_diorite_boulders");
        public static final ResourceKey<PlacedFeature> LARGE_DIORITE_BOULDERS = key("large_diorite_boulders");
    }

    public static class Vegetation {
        public static final ResourceKey<PlacedFeature> TUNDRA_FLOWERS = key("tundra_flowers");
        public static final ResourceKey<PlacedFeature> BEARBERRY_PATCH = key("bearberry_patch");
        public static final ResourceKey<PlacedFeature> SPORADIC_CANDELOPUE = key("sporadic_candeloupe");
        public static final ResourceKey<PlacedFeature> GROVE_BULBSACKS = key("grove_bulbsacks");
        public static final ResourceKey<PlacedFeature> GROVE_MEDIUM_RED_GRIMCAP = key("grove_medium_red_grimcap");
        public static final ResourceKey<PlacedFeature> GROVE_LARGE_GRIMCAP = key("grove_large_grimcap");
        public static final ResourceKey<PlacedFeature> GROVE_GIANT_GRIMCAP = key("grove_giant_grimcap");
        public static final ResourceKey<PlacedFeature> GROVE_MEDIUM_PURPLE_GRIMCAP = key("grove_medium_purple_grimcap");
        public static final ResourceKey<PlacedFeature> GROVE_MILDEW_FUZZ = key("grove_mildew_fuzz");
        public static final ResourceKey<PlacedFeature> THIN_SHORT_FLOOR_VELWOOD_TREES = key("thin_short_velwood_trees");
        public static final ResourceKey<PlacedFeature> THIN_TALL_FLOOR_VELWOOD_TREES = key("thin_tall_velwood_trees");
        public static final ResourceKey<PlacedFeature> BIG_VELWOOD_TREES = key("big_velwood_trees");
    }

    public static class Raw {
        public static final ResourceKey<PlacedFeature> SANGUITE_PILLARS = key("sanguite_pillars");
    }

    public static class Deltas {
        public static final ResourceKey<PlacedFeature> GRIMCAP_ACID = key("grimcap_acid_deltas");
    }

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Frostwork.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(
                context,
                Vegetation.THIN_SHORT_FLOOR_VELWOOD_TREES,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Vegetation.Trees.VELWOOD_SINGLE_SHORT),
                CountOnEveryLayerPlacement.of(30),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                PlacementUtils.filteredByBlockSurvival(FBlocks.VELWOOD.sapling.get()),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.THIN_TALL_FLOOR_VELWOOD_TREES,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Vegetation.Trees.VELWOOD_SINGLE_TALL),
                CountOnEveryLayerPlacement.of(10),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                PlacementUtils.filteredByBlockSurvival(FBlocks.VELWOOD.sapling.get()),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.BIG_VELWOOD_TREES,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Vegetation.Trees.VELWOOD_DOUBLE),
                CountOnEveryLayerPlacement.of(10),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                PlacementUtils.filteredByBlockSurvival(FBlocks.VELWOOD.sapling.get()),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Raw.SANGUITE_PILLARS,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Pillars.SANGUITE_PILLAR),
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                PlacementUtils.isEmpty(),
                //EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),

                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.GROVE_MILDEW_FUZZ,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Vegetation.MILDEW_FUZZ),
                NoiseBasedCountPlacement.of(120, 80.0, 0.0),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

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
                Vegetation.GROVE_MEDIUM_RED_GRIMCAP,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Fungus.MEDIUM_GRIMCAP),
                CountPlacement.of(64),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.GROVE_LARGE_GRIMCAP,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Fungus.LARGE_GRIMCAP),
                CountPlacement.of(64),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.GROVE_GIANT_GRIMCAP,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Fungus.GIANT_GRIMCAP),
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Deltas.GRIMCAP_ACID,
                configuredFeatures.getOrThrow(FConfiguredFeatures.Grimcap.SANGUITE_ACID_DELTAS),
                CountOnEveryLayerPlacement.of(40),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.SPORADIC_CANDELOPUE,
                configuredFeatures.getOrThrow(FConfiguredFeatures.VERDANT_SINGLE_CANDELOUPE),
                CountPlacement.of(64),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(
                context,
                Vegetation.GROVE_BULBSACKS,
                configuredFeatures.getOrThrow(FConfiguredFeatures.GRIMCAP_BULBSACK),
                //RarityFilter.onAverageOnceEvery(7),
                //new RidgedCountPlacement(4f, 0.7f, 999f),
                //InSquarePlacement.spread(),
                //PlacementUtils.FULL_RANGE,
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

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
