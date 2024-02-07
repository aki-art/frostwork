package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record MediumFungusFeatureConfig(IntProvider stemLength,IntProvider capRadius, Float squareChance, Holder<PlacedFeature> decoration, float decorationChance, BlockStateProvider cap, BlockStateProvider stem) implements FeatureConfiguration {
    public static Codec<MediumFungusFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IntProvider.CODEC.fieldOf("stem_length").forGetter(MediumFungusFeatureConfig::stemLength),
                    IntProvider.CODEC.fieldOf("capRadius").forGetter(MediumFungusFeatureConfig::capRadius),
                    Codec.FLOAT.fieldOf("square_chance").forGetter(MediumFungusFeatureConfig::squareChance),
                    PlacedFeature.CODEC.fieldOf("decoration").forGetter(MediumFungusFeatureConfig::decoration),
                    Codec.FLOAT.fieldOf("decoration_chance").forGetter(MediumFungusFeatureConfig::decorationChance),
                    BlockStateProvider.CODEC.fieldOf("cap_block").forGetter(MediumFungusFeatureConfig::cap),
                    BlockStateProvider.CODEC.fieldOf("stem_block").forGetter(MediumFungusFeatureConfig::stem)
            ).apply(instance, MediumFungusFeatureConfig::new));
}
