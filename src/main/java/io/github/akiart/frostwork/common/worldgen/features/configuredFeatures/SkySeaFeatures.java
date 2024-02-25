package io.github.akiart.frostwork.common.worldgen.features.configuredFeatures;

import io.github.akiart.frostwork.common.FTags;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.blockTypes.StarBrightBlock;
import io.github.akiart.frostwork.common.worldgen.features.FConfiguredFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class SkySeaFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOAPSTONE_POND = FConfiguredFeatures.key("soapstone_pond");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CEILING_LIGHTS = FConfiguredFeatures.key("ceiling_lights");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STARS = FConfiguredFeatures.key("stars");

    public static void register(BootstapContext<ConfiguredFeature<?, ?>> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry) {
        FConfiguredFeatures.register(
                context,
                SOAPSTONE_POND,
                Feature.WATERLOGGED_VEGETATION_PATCH,
                new VegetationPatchConfiguration(
                        FTags.Blocks.DOWNPOUR_GROUND_REPLACEABLE,
                        BlockStateProvider.simple(Blocks.SAND),
                        PlacementUtils.inlinePlaced(configuredFeatureRegistry.getOrThrow(AquaticFeatures.SEAGRASS_SIMPLE)),
                        CaveSurface.FLOOR,
                        ConstantInt.of(3), // depth
                        0.8F, // extraBottomBlockChance
                        5, // verticalRange
                        0.1F, // vegetationChance
                        UniformInt.of(4, 7), // xzRadius
                        0.7F // extraEdgeColumnChance
                )
        );

        FConfiguredFeatures.register(
                context,
                CEILING_LIGHTS,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.SEA_LANTERN))
        );

        FConfiguredFeatures.register(
                context,
                STARS,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        84,
                        8,
                        3,
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(
                                        new RandomizedIntStateProvider(
                                                BlockStateProvider.simple(FBlocks.STARBRIGHT.get().defaultBlockState().setValue(StarBrightBlock.FACING, Direction.DOWN)),
                                                StarBrightBlock.VARIANT,
                                                UniformInt.of(0, StarBrightBlock.VARIANT_MAX)
                                        )
                                )
                        )
                )
        );
    }
}
