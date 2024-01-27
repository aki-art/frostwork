package io.github.akiart.frostwork.common.worldgen.features;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.BlobConfig;
import io.github.akiart.frostwork.common.worldgen.features.featureTypes.BoulderFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE,
            Frostwork.MOD_ID);

    public static final DeferredHolder<Feature<?>, Feature<BlobConfig>> BOULDER = FEATURES
            .register("boulder", () -> new BoulderFeature(BlobConfig.CODEC));
}
