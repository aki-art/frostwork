package io.github.akiart.frostwork.common.worldgen;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.FTags;
import io.github.akiart.frostwork.common.init.FBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.registries.DeferredBlock;

public class FConfiguredFeatures {

    // Ores
    public static final ResourceKey<ConfiguredFeature<?, ?>> EDELSTONE_COAL_ORE = registerKey("edelstone_coal_ore");

    // Trees
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROZEN_ELM = registerKey("frozen_elm");

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(final String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Frostwork.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        registerSimpleOre(context, EDELSTONE_COAL_ORE, FTags.Blocks.EDELSTONE_REPLACEABLE, FBlocks.EDELSTONE_COAL_ORE, 9);

        register(context, FROZEN_ELM, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(FBlocks.FROZEN_ELM.log.get()),
                new StraightTrunkPlacer(5, 4, 3),

                BlockStateProvider.simple(FBlocks.FROZEN_ELM.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),

                new TwoLayersFeatureSize(1, 0, 2)).build());
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
