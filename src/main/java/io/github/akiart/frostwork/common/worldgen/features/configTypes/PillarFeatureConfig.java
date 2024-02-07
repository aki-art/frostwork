package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record PillarFeatureConfig(BlockStateProvider state, IntProvider width) implements FeatureConfiguration {
    public static final Codec<PillarFeatureConfig> CODEC = RecordCodecBuilder.create((builder -> builder
            .group(
                    BlockStateProvider.CODEC
                            .fieldOf("state")
                            .forGetter(config -> config.state),
                    IntProvider.CODEC
                            .fieldOf("width")
                            .forGetter(config -> config.width)
            )
            .apply(builder, PillarFeatureConfig::new)));

}
