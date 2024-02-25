package io.github.akiart.frostwork.common.worldgen.features.configuredFeatures;

import io.github.akiart.frostwork.common.FTags;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.features.FConfiguredFeatures;
import io.github.akiart.frostwork.common.worldgen.features.FFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class SulfurSpringsFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SULFUR_POOL = FConfiguredFeatures.key("sulfur_pool");

    public static void register(BootstapContext<ConfiguredFeature<?, ?>> context, HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry) {
        FConfiguredFeatures.register(
                context,
                SULFUR_POOL,
                FFeatures.ACID_POND.get(),
                new VegetationPatchConfiguration(
                        FTags.Blocks.SULFUR_POOL_REPLACEABLE,
                        BlockStateProvider.simple(FBlocks.SULFUR.get()),
                        PlacementUtils.inlinePlaced(configuredFeatureRegistry.getOrThrow(AquaticFeatures.SEAGRASS_SHORT)),
                        CaveSurface.FLOOR,
                        ConstantInt.of(3), // depth
                        0.8F, // extraBottomBlockChance
                        5, // verticalRange
                        0.1F, // vegetationChance
                        UniformInt.of(6, 10), // xzRadius
                        0.1F // extraEdgeColumnChance
                )
        );
    }
}
