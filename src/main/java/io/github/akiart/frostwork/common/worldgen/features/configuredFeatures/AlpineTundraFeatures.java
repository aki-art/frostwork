package io.github.akiart.frostwork.common.worldgen.features.configuredFeatures;

import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.features.FConfiguredFeatures;
import io.github.akiart.frostwork.common.worldgen.features.FFeatures;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.BlobConfig;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class AlpineTundraFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIORITE_BOULDERS = FConfiguredFeatures.key("diorite_boulders");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_DIORITE_BOULDERS = FConfiguredFeatures.key("large_diorite_boulders");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUNDRA_FLOWERS = FConfiguredFeatures.key("tundra_flowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUNDRA_BEARBERRY = FConfiguredFeatures.key("tundra_bearberry");

    public static void register(BootstapContext<ConfiguredFeature<?, ?>> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry) {
        FConfiguredFeatures.register(
                context,
                DIORITE_BOULDERS,
                FFeatures.BOULDER.get(),
                new BlobConfig(
                        BlockStateProvider.simple(Blocks.DIORITE),
                        BlockTags.DIRT,
                        true,
                        true,
                        3, UniformInt.of(0, 3)));

        FConfiguredFeatures.register(
                context,
                LARGE_DIORITE_BOULDERS,
                FFeatures.BOULDER.get(),
                new BlobConfig(BlockStateProvider.simple(Blocks.DIORITE),
                        BlockTags.DIRT,
                        true,
                        false,
                        3,
                        UniformInt.of(2, 5)));

        FConfiguredFeatures.register(
                context,
                TUNDRA_BEARBERRY,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        97,
                        10,
                        3,
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(FBlocks.BEARBERRY.get())))
                )
        );

        var spreadFlowers = new RandomPatchConfiguration(
                96,
                8,
                3,
                PlacementUtils.onlyWhenEmpty(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                new DualNoiseProvider(
                                        new InclusiveRange<>(1, 3),
                                        new NormalNoise.NoiseParameters(-10, 1.0),
                                        1.0F,
                                        2345L,
                                        new NormalNoise.NoiseParameters(-3, 1.0),
                                        1.0F,
                                        List.of(
                                                FBlocks.YARROW.get().defaultBlockState(),
                                                FBlocks.LAVENDER.get().defaultBlockState()
                                        )
                                )
                        )
                )
        );

        FConfiguredFeatures.register(
                context,
                TUNDRA_FLOWERS,
                Feature.RANDOM_PATCH,
                spreadFlowers);
    }
}
