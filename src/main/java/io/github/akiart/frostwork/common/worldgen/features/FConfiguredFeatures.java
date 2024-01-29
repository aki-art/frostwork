package io.github.akiart.frostwork.common.worldgen.features;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.FTags;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.BlobConfig;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.FungusFeatureConfig;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.Tendrils2DConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

public class FConfiguredFeatures {

    // Ores
    public static final ResourceKey<ConfiguredFeature<?, ?>> EDELSTONE_COAL_ORE = key("edelstone_coal_ore");

    // Trees
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROZEN_ELM = key("frozen_elm");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ELM = key("elm");

    // Mushrooms
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_GRIMCAP = key("medium_grimcap");

    // Vegetation
    public static final ResourceKey<ConfiguredFeature<?, ?>> FORGET_ME_KNOW_COVERAGE = key("forget_me_now_coverage");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIORITE_BOULDERS = key("diorite_boulders");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_DIORITE_BOULDERS = key("large_diorite_boulders");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUNDRA_FLOWERS = key("tundra_flowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUNDRA_BEARBERRY = key("tundra_bearberry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VERDANT_SINGLE_CANDELOUPE = key("verdant_single_candeloupe");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRIMCAP_BULBSACK = key("grimcap_bulbsack");

    private static ResourceKey<ConfiguredFeature<?, ?>> key(final String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Frostwork.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        registerSimpleOre(context, EDELSTONE_COAL_ORE, FTags.Blocks.EDELSTONE_REPLACEABLE, FBlocks.EDELSTONE_COAL_ORE, 9);

        register(context, DIORITE_BOULDERS, FFeatures.BOULDER.get(), new BlobConfig(BlockStateProvider.simple(Blocks.DIORITE), BlockTags.DIRT, true, true, 3, UniformInt.of(0, 3)));
        register(context, LARGE_DIORITE_BOULDERS, FFeatures.BOULDER.get(), new BlobConfig(BlockStateProvider.simple(Blocks.DIORITE), BlockTags.DIRT, true, false, 3,  UniformInt.of(2, 5)));


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
                VERDANT_SINGLE_CANDELOUPE,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(FBlocks.CANDELOUPE.get())));

        register(context,
                MEDIUM_GRIMCAP,
                FFeatures.FUNGUS.get(),
                new FungusFeatureConfig(
                        UniformInt.of(4, 6),
                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get()),
                        -2,
                        List.of(
                                new FungusFeatureConfig.Layer(
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.cap.get()),
                                        null,
                                        ConstantInt.of(3)
                                ),
                                new FungusFeatureConfig.Layer(
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.cap.get()),
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get()),
                                        ConstantInt.of(3)
                                ),
                                new FungusFeatureConfig.Layer(
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.cap.get()),
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.cap.get()),
                                        ConstantInt.of(2)
                                )
                        )));

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

        FeatureUtils.register(
                context,
                TUNDRA_BEARBERRY,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        97, 10, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(FBlocks.BEARBERRY.get())))
                )
        );

//        var spreadBulbSacks = new RandomPatchConfiguration(
//                256,
//                8,
//                8,
//                PlacementUtils.onlyWhenEmpty(
//                        FFeatures.BULBSACK.get(),
//                        FeatureConfiguration.NONE
//                )
//        );

        var tendrilsOfBulbs = new Tendrils2DConfig(
                UniformInt.of(9, 12),
                8f,
                0.7f,
                999f,
                PlacementUtils.onlyWhenEmpty(
                        FFeatures.BULBSACK.get(),
                        FeatureConfiguration.NONE
                )
        );

        FeatureUtils.register(
                context,
                GRIMCAP_BULBSACK,
                Feature.SIMPLE_RANDOM_SELECTOR,
                new SimpleRandomFeatureConfiguration(
                        HolderSet.direct(
                                PlacementUtils.inlinePlaced(FFeatures.TENDRILS_2D.get(), tendrilsOfBulbs)
                        )
                ));

        FeatureUtils.register(
                context,
                TUNDRA_FLOWERS,
                Feature.SIMPLE_RANDOM_SELECTOR,
                new SimpleRandomFeatureConfiguration(
                        HolderSet.direct(
                                PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, spreadFlowers)
                        )
                )
        );
    }

    private static void registerSimpleOre(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, TagKey<Block> targetBlock, DeferredBlock<Block> ore, int veinSize) {
        RuleTest replaceable = new TagMatchTest(targetBlock);
        register(context, key, Feature.ORE, new OreConfiguration(replaceable, ore.get().defaultBlockState(), veinSize));
    }

    private static <ConfigType extends FeatureConfiguration, FeatureType extends Feature<ConfigType>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
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
