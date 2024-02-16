package io.github.akiart.frostwork.common.worldgen.features;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.FTags;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.*;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
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
import net.minecraft.world.level.block.*;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;

public class FConfiguredFeatures {

    // Ores
    public static final ResourceKey<ConfiguredFeature<?, ?>> EDELSTONE_COAL_ORE = key("edelstone_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARLSTONE_COAL_ORE = key("marlstone_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARLSTONE_WOLFRAMITE_ORE = key("marlstone_wolframite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARLSTONE_BURIED_OBJECT = key("marlstone_buried_object");


    public static class Fungus {
        //public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_RED_GRIMCAP = key("small_red_grimcap");
        public static final ResourceKey<ConfiguredFeature<?, ?>> MEDIUM_GRIMCAP = key("red_medium_grimcap");
        public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GRIMCAP = key("red_large_grimcap");
        public static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_GRIMCAP = key("giant_grimcap");
        public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MEDIUM_GRIMCAP = key("purple_medium_grimcap");
    }

    // Vegetation
    public static final ResourceKey<ConfiguredFeature<?, ?>> FORGET_ME_KNOW_COVERAGE = key("forget_me_now_coverage");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIORITE_BOULDERS = key("diorite_boulders");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_DIORITE_BOULDERS = key("large_diorite_boulders");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUNDRA_FLOWERS = key("tundra_flowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUNDRA_BEARBERRY = key("tundra_bearberry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VERDANT_SINGLE_CANDELOUPE = key("verdant_single_candeloupe");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRIMCAP_BULBSACK = key("grimcap_bulbsack");

    public static class Vegetation {
        public static final ResourceKey<ConfiguredFeature<?, ?>> MILDEW_FUZZ = key("mildew_fuzz");
        public static class Trees {
            public static final ResourceKey<ConfiguredFeature<?, ?>> VELWOOD_SINGLE_SHORT = key("velwood_single_short");
            public static final ResourceKey<ConfiguredFeature<?, ?>> VELWOOD_SINGLE_TALL = key("velwood_single_tall");
            public static final ResourceKey<ConfiguredFeature<?, ?>> VELWOOD_DOUBLE = key("velwood_double");
            public static final ResourceKey<ConfiguredFeature<?, ?>> FROZEN_ELM = key("frozen_elm");
            public static final ResourceKey<ConfiguredFeature<?, ?>> ELM = key("elm");
        }
    }

    public static class Pillars {
        public static final ResourceKey<ConfiguredFeature<?, ?>> SANGUITE_PILLAR = key("sanguite_pillar");
    }

    public static class Grimcap {
        public static final ResourceKey<ConfiguredFeature<? ,? >> SANGUITE_ACID_DELTAS = key("sanguite_acid_deltas");
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> key(final String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Frostwork.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        ores(context);
        boulders(context);
        trees(context);
        plants(context);
        pillars(context);

        grimcap(context);

    }
     // HolderSet.direct(
    private static void grimcap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context,
                Grimcap.SANGUITE_ACID_DELTAS,
                Feature.DELTA_FEATURE,
                new DeltaFeatureConfiguration(FBlocks.ACID.get().defaultBlockState(), Blocks.CALCITE.defaultBlockState(), UniformInt.of(3, 7), UniformInt.of(0, 2)));
    }

    private static void pillars(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context,
                Pillars.SANGUITE_PILLAR,
                FFeatures.PILLAR.get(),
                new PillarFeatureConfig(BlockStateProvider.simple(FBlocks.SANGUITE.block.get()), UniformInt.of(2, 4)));
    }

    private static void ores(BootstapContext<ConfiguredFeature<?, ?>> context) {
        registerSimpleOre(context, EDELSTONE_COAL_ORE, FTags.Blocks.EDELSTONE_REPLACEABLE, FBlocks.EDELSTONE_COAL_ORE, 17);

        registerSimpleOre(context, MARLSTONE_COAL_ORE, FBlocks.MARLSTONE.block.get(), FBlocks.MARLSTONE_COAL_ORE, 17);
        registerSimpleOre(context, MARLSTONE_WOLFRAMITE_ORE, FBlocks.MARLSTONE.block.get(), FBlocks.MARLSTONE_WOLFRAMITE_ORE, 7);
        registerSimpleOre(context, MARLSTONE_BURIED_OBJECT, FBlocks.MARLSTONE.block.get(), FBlocks.MARLSTONE_BURIED_OBJECT, 1);
    }

    private static void boulders(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, DIORITE_BOULDERS, FFeatures.BOULDER.get(), new BlobConfig(BlockStateProvider.simple(Blocks.DIORITE), BlockTags.DIRT, true, true, 3, UniformInt.of(0, 3)));
        register(context, LARGE_DIORITE_BOULDERS, FFeatures.BOULDER.get(), new BlobConfig(BlockStateProvider.simple(Blocks.DIORITE), BlockTags.DIRT, true, false, 3,  UniformInt.of(2, 5)));
    }

    private static void trees(BootstapContext<ConfiguredFeature<?, ?>> context) {

        register(context,
                Vegetation.Trees.VELWOOD_SINGLE_SHORT,
                FFeatures.VELWOOD_TREE.get(),
                new VelwoodTreeFeatureConfig(
                        1,
                        UniformInt.of(8, 15),
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

        register(context,
                Vegetation.Trees.VELWOOD_SINGLE_TALL,
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

        register(context,
                Vegetation.Trees.VELWOOD_DOUBLE,
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
                VERDANT_SINGLE_CANDELOUPE,
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(FBlocks.CANDELOUPE.get())));

        register(context,
                Vegetation.MILDEW_FUZZ,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(97, 10, 3, PlacementUtils.inlinePlaced(
                    Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(BlockStateProvider.simple(FBlocks.MILDEW_FUZZ.get())))));

        register(context,
                Fungus.MEDIUM_GRIMCAP,
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
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get())
                                )),
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
                                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get())
                                )
                        )
                )
        ) ;


        register(context,
                Fungus.PURPLE_MEDIUM_GRIMCAP,
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
                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get())
                ));

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
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        97, 10, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(FBlocks.BEARBERRY.get())))
                )
        );

        FeatureUtils.register(
                context,
                Fungus.GIANT_GRIMCAP,
                FFeatures.GIANT_FUNGUS.get(),
                new GiantFungusFeatureConfig(
                        List.of(
                                new ResourceLocation(Frostwork.MOD_ID, "fungal/giant_grimcap_red_cap")
                        ),
                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get()),
                        UniformInt.of(10, 24),
                        3,
                        UniformInt.of(4, 6)

                )
        );

        FeatureUtils.register(
                context,
                Fungus.LARGE_GRIMCAP,
                FFeatures.BIG_FUNGUS.get(),
                new BigFungusFeatureConfig(
                        List.of(
                                new ResourceLocation(Frostwork.MOD_ID, "fungal/red_medium_grimcap_top")
                        ),
                        UniformInt.of(4, 7),
                        BlockStateProvider.simple(FBlocks.GRIMCAP.stem.get()),
                        0
                )
        );

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
                FFeatures.TENDRILS.get(),
                tendrilsOfBulbs);

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

    private static void registerSimpleOre(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, Block targetBlock, DeferredBlock<Block> ore, int veinSize) {
        RuleTest replaceable = new BlockMatchTest(targetBlock);
        register(context, key, Feature.ORE, new OreConfiguration(replaceable, ore.get().defaultBlockState(), veinSize));
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
