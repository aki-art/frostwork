package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class BlobConfig  implements FeatureConfiguration {
    public final boolean surfaceOnly;
    public final boolean manhattan;
    public final BlockStateProvider block;
    public final TagKey<Block> target;
    private final UniformInt radius;
    public final float amplitude;

    public static final Codec<BlobConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder
                .group(
                        BlockStateProvider.CODEC.fieldOf("block").forGetter((config) -> config.block),
                        TagKey.hashedCodec(Registries.BLOCK).fieldOf("target").forGetter((config) -> config.target),
                        Codec.BOOL.fieldOf("surfaceOnly").forGetter((config) -> config.surfaceOnly),
                        Codec.BOOL.fieldOf("manhattan").forGetter((config) -> config.manhattan),
                        Codec.FLOAT.fieldOf("y_variance").forGetter((config) -> config.amplitude),
                        UniformInt.CODEC.fieldOf("radius").forGetter((config) -> config.radius))
                .apply(builder, BlobConfig::new);
    });

    public BlobConfig(BlockStateProvider block, TagKey<Block> target, boolean surfaceOnly, boolean manhattan, float amplitude, UniformInt radius) {
        this.block = block;
        this.surfaceOnly = surfaceOnly;
        this.radius = radius;
        this.target = target;
        this.amplitude = amplitude;
        this.manhattan = manhattan;
    }
    public UniformInt getRadius() {
        return this.radius;
    }

}
