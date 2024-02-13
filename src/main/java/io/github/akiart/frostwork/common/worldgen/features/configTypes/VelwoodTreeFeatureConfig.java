package io.github.akiart.frostwork.common.worldgen.features.configTypes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.List;

public record VelwoodTreeFeatureConfig(int width, IntProvider height, BlockStateProvider trunk, List<FoliageStructure> foliageStructuresUpwards, List<FoliageStructure> foliageStructuresDownwards) implements FeatureConfiguration {
    public static Codec<VelwoodTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("width").forGetter(VelwoodTreeFeatureConfig::width),
                    IntProvider.CODEC.fieldOf("height").forGetter(VelwoodTreeFeatureConfig::height),
                    BlockStateProvider.CODEC.fieldOf("trunk").forGetter(VelwoodTreeFeatureConfig::trunk),
                    FoliageStructure.CODEC.listOf().fieldOf("foliage_structures_upside").forGetter(VelwoodTreeFeatureConfig::foliageStructuresUpwards),
                    FoliageStructure.CODEC.listOf().fieldOf("foliage_structures_downside").forGetter(VelwoodTreeFeatureConfig::foliageStructuresDownwards)
            ).apply(instance, VelwoodTreeFeatureConfig::new));

    public record FoliageStructure(ResourceLocation structure, Vec3i offset) {
        public static Codec<FoliageStructure> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        ResourceLocation.CODEC.fieldOf("structure").forGetter(FoliageStructure::structure),
                        Vec3i.CODEC.fieldOf("offset").forGetter(FoliageStructure::offset)
                ).apply(instance, FoliageStructure::new));
    }
}
