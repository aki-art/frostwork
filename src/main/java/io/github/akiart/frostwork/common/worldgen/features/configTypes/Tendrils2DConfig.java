package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record Tendrils2DConfig(IntProvider radius, float frequency, float cutoffMin, float cutoffMax, Holder<PlacedFeature> feature) implements FeatureConfiguration {
    public static Codec<Tendrils2DConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    IntProvider.CODEC.fieldOf("radius").forGetter(Tendrils2DConfig::radius),
                    Codec.FLOAT.fieldOf("frequency").forGetter(Tendrils2DConfig::frequency),
                    Codec.FLOAT.fieldOf("cutoff_min").forGetter(Tendrils2DConfig::cutoffMin),
                    Codec.FLOAT.fieldOf("cutoff_max").forGetter(Tendrils2DConfig::cutoffMax),
                    PlacedFeature.CODEC.fieldOf("feature").forGetter(Tendrils2DConfig::feature)
            ).apply(instance, Tendrils2DConfig::new));
}
