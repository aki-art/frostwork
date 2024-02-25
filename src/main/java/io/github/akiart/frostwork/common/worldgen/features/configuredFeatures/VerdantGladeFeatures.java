package io.github.akiart.frostwork.common.worldgen.features.configuredFeatures;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.blockTypes.InfectedVelmiteLogBlock;
import io.github.akiart.frostwork.common.worldgen.features.FConfiguredFeatures;
import io.github.akiart.frostwork.common.worldgen.features.FFeatures;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.StemmedPlantFeatureConfig;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.VelwoodTreeFeatureConfig;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.List;

public class VerdantGladeFeatures {
    public static class Vegetation {

        public static final ResourceKey<ConfiguredFeature<?, ?>> VELWOOD_SINGLE_SHORT = FConfiguredFeatures.key("velwood_single_short");
        public static final ResourceKey<ConfiguredFeature<?, ?>> VELWOOD_SINGLE_TALL = FConfiguredFeatures.key("velwood_single_tall");
        public static final ResourceKey<ConfiguredFeature<?, ?>> VELWOOD_DOUBLE = FConfiguredFeatures.key("velwood_double");
        public static final ResourceKey<ConfiguredFeature<?, ?>> CANDELOPUE = FConfiguredFeatures.key("candeloupe_with_stem");
        public static final ResourceKey<ConfiguredFeature<?, ?>> FERN_PATCH = FConfiguredFeatures.key("verdant_fern_patch");
        public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_PATCH = FConfiguredFeatures.key("verdant_mossy_patch");
    }

    public static class Decoration {public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTALLIZED_MUD_CLUSTER = FConfiguredFeatures.key("crystallized_mud_speleothem_cluster");
    }

    public static void register(BootstapContext<ConfiguredFeature<?, ?>> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry) {

        FConfiguredFeatures.register(
                context,
                Vegetation.FERN_PATCH,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(Blocks.FERN.defaultBlockState(), 50)
                                        .add(Blocks.LARGE_FERN.defaultBlockState(), 50)
                                        .add(Blocks.MOSS_CARPET.defaultBlockState(), 25)
                                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 50)
                                        .add(Blocks.TALL_GRASS.defaultBlockState(), 10)
                        )
                )
        );

        FConfiguredFeatures.register(
                context,
                Vegetation.MOSSY_PATCH,
                Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        BlockTags.MOSS_REPLACEABLE,
                        BlockStateProvider.simple(Blocks.MOSS_BLOCK),
                        PlacementUtils.inlinePlaced(configuredFeatureRegistry.getOrThrow(Vegetation.FERN_PATCH)),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        5,
                        0.8F,
                        UniformInt.of(4, 7),
                        0.3F
                )
        );

        FConfiguredFeatures.register(context,
                Vegetation.VELWOOD_SINGLE_SHORT,
                FFeatures.VELWOOD_TREE.get(),
                new VelwoodTreeFeatureConfig(
                        1,
                        UniformInt.of(8, 15),
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(FBlocks.VELWOOD.log.get().defaultBlockState(), 10)
                                        .add(FBlocks.INFESTED_VELWOOD.get().defaultBlockState().setValue(InfectedVelmiteLogBlock.ACTIVE, true), 1)
                        ),
                        List.of(
                                new VelwoodTreeFeatureConfig.FoliageStructure(
                                        new ResourceLocation(Frostwork.MOD_ID, "tree/velwood_top_a"),
                                        new Vec3i(3, 0, 3)
                                ),
                                new VelwoodTreeFeatureConfig.FoliageStructure(
                                        new ResourceLocation(Frostwork.MOD_ID, "tree/velwood_top_b"),
                                        new Vec3i(3, 0, 3)
                                )
                        ),
                        List.of(
                                new VelwoodTreeFeatureConfig.FoliageStructure(
                                        new ResourceLocation(Frostwork.MOD_ID, "tree/velwood_tree_top_uspidedown_a"),
                                        new Vec3i(3, 0, 3)
                                )
                        )
                ));

        FConfiguredFeatures.register(context,
                Vegetation.VELWOOD_SINGLE_TALL,
                FFeatures.VELWOOD_TREE.get(),
                new VelwoodTreeFeatureConfig(
                        1,
                        UniformInt.of(15, 60),
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(FBlocks.VELWOOD.log.get().defaultBlockState(), 10)
                                        .add(FBlocks.INFESTED_VELWOOD.get().defaultBlockState(), 1)
                        ),
                        List.of(
                                new VelwoodTreeFeatureConfig.FoliageStructure(
                                        new ResourceLocation(Frostwork.MOD_ID, "tree/velwood_top_a"),
                                        new Vec3i(3, 0, 3)
                                ),
                                new VelwoodTreeFeatureConfig.FoliageStructure(
                                        new ResourceLocation(Frostwork.MOD_ID, "tree/velwood_top_b"),
                                        new Vec3i(3, 0, 3)
                                )
                        ),
                        List.of(
                                new VelwoodTreeFeatureConfig.FoliageStructure(
                                        new ResourceLocation(Frostwork.MOD_ID, "tree/velwood_tree_top_uspidedown_a"),
                                        new Vec3i(3, 0, 3)
                                )
                        )
                ));

        FConfiguredFeatures.register(context,
                Vegetation.VELWOOD_DOUBLE,
                FFeatures.VELWOOD_TREE.get(),
                new VelwoodTreeFeatureConfig(
                        2,
                        UniformInt.of(30, 60),
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(FBlocks.VELWOOD.log.get().defaultBlockState(), 10)
                                        .add(FBlocks.INFESTED_VELWOOD.get().defaultBlockState(), 1)
                        ),
                        List.of(
                                new VelwoodTreeFeatureConfig.FoliageStructure(
                                        new ResourceLocation(Frostwork.MOD_ID, "tree/velwood_big_foliage_up"),
                                        new Vec3i(4, 0, 4)
                                )
                        ),
                        List.of(
                                new VelwoodTreeFeatureConfig.FoliageStructure(
                                        new ResourceLocation(Frostwork.MOD_ID, "tree/velwood_tree_top_uspidedown_a"),
                                        new Vec3i(3, 0, 3)
                                )
                        )
                ));

        FConfiguredFeatures.register(context,
                Vegetation.CANDELOPUE,
                FFeatures.STEMMED_FEATURE.get(),
                new StemmedPlantFeatureConfig(
                        BlockStateProvider.simple(FBlocks.ATTACHED_CANDELOUPE_STEM.get()),
                        BlockStateProvider.simple(FBlocks.CANDELOUPE.get()))
        );

    }
}
