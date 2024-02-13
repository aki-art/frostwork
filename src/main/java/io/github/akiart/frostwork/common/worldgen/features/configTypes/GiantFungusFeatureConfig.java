package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record GiantFungusFeatureConfig(List<ResourceLocation> capStructures, BlockStateProvider capBlock, IntProvider stemLength, int stemStartRadius, IntProvider stemEndRadius) implements FeatureConfiguration {
    public static Codec<GiantFungusFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.listOf().fieldOf("cap_structures").forGetter(fungus -> fungus.capStructures),
                    BlockStateProvider.CODEC.fieldOf("cap_block").forGetter(GiantFungusFeatureConfig::capBlock),
                    IntProvider.CODEC.fieldOf("stem_length").forGetter(fungus -> fungus.stemLength),
                    Codec.INT.fieldOf("stem_start_radius").forGetter(fungus -> fungus.stemStartRadius),
                    IntProvider.CODEC.fieldOf("stem_end_radius").forGetter(fungus -> fungus.stemEndRadius)
            ).apply(instance, GiantFungusFeatureConfig::new)
    );
}
