package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record StemmedPlantFeatureConfig(BlockStateProvider fruit, BlockStateProvider stem) implements FeatureConfiguration {
    public static final Codec<StemmedPlantFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> builder
            .group(
                    BlockStateProvider.CODEC.fieldOf("fruit").forGetter(StemmedPlantFeatureConfig::fruit),
                    BlockStateProvider.CODEC.fieldOf("stem").forGetter(StemmedPlantFeatureConfig::stem)
            ).apply(builder, StemmedPlantFeatureConfig::new));
}
