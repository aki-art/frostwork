package io.github.akiart.frostwork.common.worldgen.features.configuredFeatures;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.features.FConfiguredFeatures;
import io.github.akiart.frostwork.common.worldgen.features.FFeatures;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public class GrimcapFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_GRIMCAP = FConfiguredFeatures.key("red_medium_grimcap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GRIMCAP = FConfiguredFeatures.key("red_large_grimcap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_GRIMCAP = FConfiguredFeatures.key("giant_grimcap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MEDIUM_GRIMCAP = FConfiguredFeatures.key("purple_medium_grimcap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MILDEW_FUZZ = FConfiguredFeatures.key("mildew_fuzz");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GRIMCAP_BULBSACK = FConfiguredFeatures.key("grimcap_bulbsack");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SANGUITE_PILLAR = FConfiguredFeatures.key("sanguite_pillar");
    public static final ResourceKey<ConfiguredFeature<? ,? >> SANGUITE_ACID_DELTAS = FConfiguredFeatures.key("sanguite_acid_deltas");

    public static void register(BootstapContext<ConfiguredFeature<?, ?>> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry) {
        FConfiguredFeatures.register(context,
                SANGUITE_ACID_DELTAS,
                Feature.DELTA_FEATURE,
                new DeltaFeatureConfiguration(FBlocks.ACID.get().defaultBlockState(),
                        Blocks.CALCITE.defaultBlockState(),
                        UniformInt.of(3, 7),
                        UniformInt.of(0, 2)));

        FConfiguredFeatures.register(context,
                MILDEW_FUZZ,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(97, 10, 3, PlacementUtils.inlinePlaced(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(FBlocks.MILDEW_FUZZ.get())))));

        FConfiguredFeatures.register(context,
                MEDIUM_GRIMCAP,
                Feature.RANDOM_BOOLEAN_SELECTOR,
                new RandomBooleanFeatureConfiguration(
                        PlacementUtils.inlinePlaced(
                                FFeatures.MEDIUM_FUNGUS.get(),
                                new MediumFungusFeatureConfig(
                                        UniformInt.of(1, 3),
                                        UniformInt.of(0, 1),
                                        1f,
                                        PlacementUtils.inlinePlaced(
                                                FFeatures.BULBSACK.get(),
                                                new NoneFeatureConfiguration()),
                                        0.1f,
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.cap.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false)),
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get()))),
                        PlacementUtils.inlinePlaced(
                                FFeatures.MEDIUM_FUNGUS.get(),
                                new MediumFungusFeatureConfig(
                                        UniformInt.of(1, 3),
                                        UniformInt.of(0, 1),
                                        1f,
                                        PlacementUtils.inlinePlaced(
                                                FFeatures.BULBSACK.get(),
                                                new NoneFeatureConfiguration()),
                                        0.1f,
                                        BlockStateProvider.simple(FBlocks.PURPLE_GRIMCAP_CAP.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false)),
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get())))));

        FConfiguredFeatures.register(
                context,
                GRIMCAP_BULBSACK,
                FFeatures.TENDRILS.get(),
                new Tendrils2DConfig(
                        UniformInt.of(9, 12),
                        8f,
                        0.7f,
                        999f,
                        PlacementUtils.onlyWhenEmpty(
                                FFeatures.BULBSACK.get(),
                                FeatureConfiguration.NONE)));

        FConfiguredFeatures.register(context,
                PURPLE_MEDIUM_GRIMCAP,
                FFeatures.MEDIUM_FUNGUS.get(),
                new MediumFungusFeatureConfig(
                        UniformInt.of(1, 3),
                        UniformInt.of(0, 1),
                        1f,
                        PlacementUtils.inlinePlaced(
                                FFeatures.BULBSACK.get(),
                                new NoneFeatureConfiguration()),
                        0.1f,
                        BlockStateProvider.simple(FBlocks.PURPLE_GRIMCAP_CAP.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false)),
                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get())));

        FConfiguredFeatures.register(
                context,
                GIANT_GRIMCAP,
                FFeatures.GIANT_FUNGUS.get(),
                new GiantFungusFeatureConfig(
                        List.of(
                                new ResourceLocation(Frostwork.MOD_ID, "fungal/giant_grimcap_red_cap")),
                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get()),
                        UniformInt.of(10, 24),
                        3,
                        UniformInt.of(4, 6)));

        FConfiguredFeatures.register(
                context,
                LARGE_GRIMCAP,
                FFeatures.BIG_FUNGUS.get(),
                new BigFungusFeatureConfig(
                        List.of(
                                new ResourceLocation(Frostwork.MOD_ID, "fungal/red_medium_grimcap_top")
                        ),
                        UniformInt.of(4, 7),
                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get()),
                        0));

        FConfiguredFeatures.register(context,
                SANGUITE_PILLAR,
                FFeatures.PILLAR.get(),
                new PillarFeatureConfig(BlockStateProvider.simple(FBlocks.SANGUITE.block.get()),
                        UniformInt.of(2, 4)));
    }
}
