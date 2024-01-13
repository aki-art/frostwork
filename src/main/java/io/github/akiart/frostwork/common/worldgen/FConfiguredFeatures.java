package io.github.akiart.frostwork.common.worldgen;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.FTags;
import io.github.akiart.frostwork.common.block.FBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

public class FConfiguredFeatures {

    // Ores
    public static final ResourceKey<ConfiguredFeature<?, ?>> EDELSTONE_COAL_ORE = registerKey("edelstone_coal_ore");

    // Trees
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROZEN_ELM = registerKey("frozen_elm");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ELM = registerKey("elm");

    // Vegetation
    public static final ResourceKey<ConfiguredFeature<?, ?>> FORGET_ME_KNOW_COVERAGE = registerKey("forget_me_now_coverage");

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(final String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Frostwork.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        registerSimpleOre(context, EDELSTONE_COAL_ORE, FTags.Blocks.EDELSTONE_REPLACEABLE, FBlocks.EDELSTONE_COAL_ORE, 9);

        register(context, FROZEN_ELM, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FBlocks.FROZEN_ELM.log.get()),
                new StraightTrunkPlacer(6, 2, 2),
                BlockStateProvider.simple(FBlocks.FROZEN_ELM.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1))
                .decorators(List.of(new AlterGroundDecorator(BlockStateProvider.simple(
                        FBlocks.FROZEN_DIRT.get()
                                .defaultBlockState()
                                .setValue(SnowyDirtBlock.SNOWY, true)
                ))))
                .dirt(BlockStateProvider.simple(
                        FBlocks.FROZEN_DIRT.get()
                                .defaultBlockState()
                                .setValue(SnowyDirtBlock.SNOWY, true)
                ))
                .build());

        register(context, ELM, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FBlocks.ELM.log.get()),
                new StraightTrunkPlacer(6, 2, 2),
                BlockStateProvider.simple(FBlocks.ELM.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).build());

        register(context,
                FORGET_ME_KNOW_COVERAGE,
                Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(
                        32,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                            new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(Blocks.SHORT_GRASS.defaultBlockState(), 1)
                                        .add(FBlocks.FORGET_ME_NOW.get().defaultBlockState(), 10))
                        )
                )));
    }

    private static void registerSimpleOre(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, TagKey<Block> targetBlock, DeferredBlock<Block> ore, int veinSize) {
        RuleTest replaceable = new TagMatchTest(targetBlock);
        register(context, key, Feature.ORE, new OreConfiguration(replaceable, ore.get().defaultBlockState(), veinSize));
    }

    private static <ConfigType extends FeatureConfiguration, FeatureType extends Feature<ConfigType>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, FeatureType feature, ConfigType configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
