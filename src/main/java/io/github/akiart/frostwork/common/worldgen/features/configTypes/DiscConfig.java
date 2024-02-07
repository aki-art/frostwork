package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record DiscConfig(IntProvider radius, BlockStateProvider block) implements FeatureConfiguration {
    public static Codec<DiscConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IntProvider.CODEC.fieldOf("radius").forGetter(DiscConfig::radius),
                    BlockStateProvider.CODEC.fieldOf("block").forGetter(DiscConfig::block)
            ).apply(instance, DiscConfig::new)
    );
}
