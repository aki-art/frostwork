package io.github.akiart.frostwork.common.worldgen;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.stream.Stream;

public class FantasiaBiomeSource extends BiomeSource {

    private static final MapCodec<Holder<Biome>> ENTRY_CODEC = Biome.CODEC.fieldOf("biome");
    private FastNoiseLite testFloatingBiomes;
    private final Climate.ParameterList<Holder<Biome>> surfaceParameters;
    private final Climate.ParameterList<Holder<Biome>> caveParameters;
    public static final Codec<FantasiaBiomeSource> DATA_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Climate.ParameterList.codec(ENTRY_CODEC).fieldOf("surface_biomes").forGetter(biomeSource -> biomeSource.surfaceParameters),
                    Climate.ParameterList.codec(ENTRY_CODEC).fieldOf("cave_biomes").forGetter(biomeSource -> biomeSource.caveParameters))
                    .apply(instance, FantasiaBiomeSource::new)
    );

    public static FantasiaBiomeSource createFromList(Climate.ParameterList<Holder<Biome>> surfaceBiomes, Climate.ParameterList<Holder<Biome>> caveBiomes) {
        return new FantasiaBiomeSource(surfaceBiomes, caveBiomes);
    }

    public FantasiaBiomeSource(Climate.ParameterList<Holder<Biome>> surfaceParameters, Climate.ParameterList<Holder<Biome>> caveParameters) {
        this.surfaceParameters = surfaceParameters;
        this.caveParameters = caveParameters;

        this.testFloatingBiomes = new FastNoiseLite(0);
        testFloatingBiomes.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        testFloatingBiomes.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
        testFloatingBiomes.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
    }

    @Override
    public Codec<? extends BiomeSource> codec() {
        return DATA_CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        var surface = this.surfaceParameters.values().stream().map(Pair::getSecond);
        var cave = this.caveParameters.values().stream().map(Pair::getSecond);

        return Stream.concat(surface, cave);
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {

        // harder separation between surface and cave biomes.
        var actualY = y * 4;

        var surface = 220;
        var buffer = 10;

        var surfaceSample = sampler.sample(x, surface / 4, z);
        var depth = surfaceSample.depth(); // float output is multiplied by 10_000 here
        var offset = surface + (depth / 100) + buffer;

        var isSurface = actualY > offset;

        if(isSurface)
            return surfaceParameters.findValue(surfaceSample);
        else {
            if(actualY > 120) {
                return caveParameters.values().get(1).getSecond();
            }
            return caveParameters.findValue(sampler.sample(x, y, z));
        }
    }
}
