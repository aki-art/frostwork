package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record BigFungusFeatureConfig(List<ResourceLocation> capStructures, IntProvider stemLength, BlockStateProvider stemBlock, int capOffset) implements FeatureConfiguration {
    public static Codec<BigFungusFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.listOf().fieldOf("cap_structures").forGetter(fungus -> fungus.capStructures),
                    IntProvider.CODEC.fieldOf("stem_length").forGetter(fungus -> fungus.stemLength),
                    BlockStateProvider.CODEC.fieldOf("stem_block").forGetter(fungus -> fungus.stemBlock),
                    Codec.INT.fieldOf("cap_y_offset").forGetter(fungus -> fungus.capOffset)
            ).apply(instance, BigFungusFeatureConfig::new)
    );

}
