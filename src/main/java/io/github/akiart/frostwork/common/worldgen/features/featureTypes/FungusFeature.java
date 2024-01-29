package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.FungusFeatureConfig;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class FungusFeature extends Feature<FungusFeatureConfig> {
    public FungusFeature(Codec<FungusFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<FungusFeatureConfig> context) {
        return false;
    }
}
