package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class FungusFeatureConfig implements FeatureConfiguration {
    private final List<Layer> layers;
    private final IntProvider stemLength;
    private final int capOffset;
    private final BlockStateProvider stemBlock;

    public static Codec<FungusFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IntProvider.CODEC.fieldOf("stem_length").forGetter(fungus -> fungus.stemLength),
                    BlockStateProvider.CODEC.fieldOf("stem_block").forGetter(fungus -> fungus.stemBlock),
                    Codec.INT.fieldOf("cap_y_offset").forGetter(fungus -> fungus.capOffset),
                    Layer.CODEC.listOf().fieldOf("cap_layers").forGetter(fungus -> fungus.layers)
            ).apply(instance, FungusFeatureConfig::new)
    );

    public FungusFeatureConfig(IntProvider stemLength, BlockStateProvider stemBlock, int capOffset, List<Layer> layers) {
        this.layers = layers;
        this.stemBlock = stemBlock;
        this.stemLength = stemLength;
        this.capOffset = capOffset;
    }

    public static class Layer {
        private final IntProvider radius;
        private final BlockStateProvider block;
        @Nullable private final BlockStateProvider innerBlock;

        public static final Codec<Layer> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        BlockStateProvider.CODEC.fieldOf("block").forGetter(layer -> layer.block),
                        BlockStateProvider.CODEC.optionalFieldOf("inner_block").forGetter(layer -> Optional.ofNullable(layer.innerBlock)),
                        IntProvider.CODEC.fieldOf("radius").forGetter(layer -> layer.radius)
                ).apply(instance, (block, inner, radius) -> new Layer(block, inner.orElse(null), radius))
        );

        public Layer(BlockStateProvider block, @Nullable BlockStateProvider innerBlock, IntProvider radius) {
            this.block = block;
            this.innerBlock = innerBlock;
            this.radius = radius;
        }
    }
}
