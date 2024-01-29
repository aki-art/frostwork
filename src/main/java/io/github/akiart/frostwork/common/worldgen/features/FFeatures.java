package io.github.akiart.frostwork.common.worldgen.features;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.BlobConfig;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.FungusFeatureConfig;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.Tendrils2DConfig;
import io.github.akiart.frostwork.common.worldgen.features.featureTypes.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE,
            Frostwork.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<BlobConfig>> BOULDER = FEATURES.register("boulder", () -> new BoulderFeature(BlobConfig.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<FungusFeatureConfig>> FUNGUS = FEATURES.register("fungus", () -> new FungusFeature(FungusFeatureConfig.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> BULBSACK = FEATURES.register("bulbsack", () -> new BulbSackFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<Tendrils2DConfig>> TENDRILS_2D = FEATURES.register("tendrils_2d", () -> new Tendrils2DFeature(Tendrils2DConfig.CODEC));
    public static final DeferredHolder<Feature<?>, Feature<Tendrils2DConfig>> TENDRILS = FEATURES.register("tendrils", () -> new TendrilFeature(Tendrils2DConfig.CODEC));
}
