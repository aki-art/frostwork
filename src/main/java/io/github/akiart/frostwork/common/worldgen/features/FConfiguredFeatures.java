package io.github.akiart.frostwork.common.worldgen.features;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.FTags;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.features.configuredFeatures.*;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
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
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

public class FConfiguredFeatures {

    // Ores
    public static final ResourceKey<ConfiguredFeature<?, ?>> EDELSTONE_COAL_ORE = key("edelstone_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARLSTONE_COAL_ORE = key("marlstone_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARLSTONE_WOLFRAMITE_ORE = key("marlstone_wolframite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARLSTONE_BURIED_OBJECT = key("marlstone_buried_object");

    // Vegetation
    public static final ResourceKey<ConfiguredFeature<?, ?>> FORGET_ME_KNOW_COVERAGE = key("forget_me_now_coverage");

    public static class Vegetation {
        public static class Trees {
            public static final ResourceKey<ConfiguredFeature<?, ?>> FROZEN_ELM = key("frozen_elm");
            public static final ResourceKey<ConfiguredFeature<?, ?>> ELM = key("elm");
        }
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> key(final String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Frostwork.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        ores(context);
        trees(context);
        plants(context);

        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureRegistry = context.lookup(Registries.CONFIGURED_FEATURE);

        VerdantGladeFeatures.register(context, configuredFeatureRegistry);
        AlpineTundraFeatures.register(context, configuredFeatureRegistry);
        SkySeaFeatures.register(context, configuredFeatureRegistry);
        GrimcapFeatures.register(context, configuredFeatureRegistry);
        SulfurSpringsFeatures.register(context, configuredFeatureRegistry);
    }

    private static void ores(BootstapContext<ConfiguredFeature<?, ?>> context) {
        registerSimpleOre(context, EDELSTONE_COAL_ORE, FTags.Blocks.EDELSTONE_REPLACEABLE, FBlocks.EDELSTONE_COAL_ORE, 17);

        registerSimpleOre(context, MARLSTONE_COAL_ORE, FBlocks.MARLSTONE.block.get(), FBlocks.MARLSTONE_COAL_ORE, 17);
        registerSimpleOre(context, MARLSTONE_WOLFRAMITE_ORE, FBlocks.MARLSTONE.block.get(), FBlocks.MARLSTONE_WOLFRAMITE_ORE, 7);
        registerSimpleOre(context, MARLSTONE_BURIED_OBJECT, FBlocks.MARLSTONE.block.get(), FBlocks.MARLSTONE_BURIED_OBJECT, 1);
    }

    private static void trees(BootstapContext<ConfiguredFeature<?, ?>> context) {



        register(context, Vegetation.Trees.FROZEN_ELM, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
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

        register(context, Vegetation.Trees.ELM, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FBlocks.ELM.log.get()),
                new StraightTrunkPlacer(6, 2, 2),
                BlockStateProvider.simple(FBlocks.ELM.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).build());
    }

    private static void plants(BootstapContext<ConfiguredFeature<?, ?>> context) {



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

    private static void registerSimpleOre(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Block targetBlock, DeferredBlock<Block> ore, int veinSize) {
        RuleTest replaceable = new BlockMatchTest(targetBlock);
        register(context, key, Feature.ORE, new OreConfiguration(replaceable, ore.get().defaultBlockState(), veinSize));
    }

    private static void registerSimpleOre(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, TagKey<Block> targetBlock, DeferredBlock<Block> ore, int veinSize) {
        RuleTest replaceable = new TagMatchTest(targetBlock);
        register(context, key, Feature.ORE, new OreConfiguration(replaceable, ore.get().defaultBlockState(), veinSize));
    }

    public static <ConfigType extends FeatureConfiguration, FeatureType extends Feature<ConfigType>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, FeatureType feature, ConfigType configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
    private static BlockPredicate simplePatchPredicate(List<Block> pBlocks) {
        BlockPredicate blockpredicate;
        if (!pBlocks.isEmpty()) {
            blockpredicate = BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Direction.DOWN.getNormal(), pBlocks));
        } else {
            blockpredicate = BlockPredicate.ONLY_IN_AIR_PREDICATE;
        }

        return blockpredicate;
    }

}
