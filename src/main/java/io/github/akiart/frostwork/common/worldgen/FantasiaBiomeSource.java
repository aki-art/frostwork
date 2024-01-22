package io.github.akiart.frostwork.common.worldgen;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.*;

import java.util.stream.Stream;

public class FantasiaBiomeSource extends BiomeSource {
    public String someValue;

    private static final MapCodec<Holder<Biome>> ENTRY_CODEC = Biome.CODEC.fieldOf("biome");
    public static final MapCodec<Climate.ParameterList<Holder<Biome>>> DIRECT_CODEC = Climate.ParameterList.codec(ENTRY_CODEC).fieldOf("surface_biomes");
    private static final MapCodec<Holder<MultiNoiseBiomeSourceParameterList>> PRESET_CODEC = MultiNoiseBiomeSourceParameterList.CODEC.fieldOf("preset").withLifecycle(Lifecycle.stable());
    public static final Codec<FantasiaBiomeSource> CODEC = Codec
            .mapEither(DIRECT_CODEC, PRESET_CODEC)
            .xmap(FantasiaBiomeSource::new, (biomeSource) -> biomeSource.parameters).codec();

    private FastNoiseLite testFloatingBiomes;

    private final Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> parameters;

    public static FantasiaBiomeSource createFromList(Climate.ParameterList<Holder<Biome>> pParameters) {
        return new FantasiaBiomeSource(Either.left(pParameters));
    }

    public FantasiaBiomeSource(Either<Climate.ParameterList<Holder<Biome>>, Holder<MultiNoiseBiomeSourceParameterList>> parameters) {
        this.parameters = parameters;
        this.testFloatingBiomes = new FastNoiseLite(0);
        testFloatingBiomes.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        testFloatingBiomes.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.Hybrid);
        testFloatingBiomes.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
    }
//    public FantasiaBiomeSource(String someValue) {
//
//    }

//    // todo: actually take in data
//    public static final Codec<FantasiaBiomeSource> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            Codec.STRING.fieldOf("someValue").forGetter(o -> o.someValue)
//    ).apply(instance, FantasiaBiomeSource::new));

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return this.parameters().values().stream().map(Pair::getSecond);
    }

    private Climate.ParameterList<Holder<Biome>> parameters() {
        return this.parameters.map((biomes) -> biomes, (paramteres) -> paramteres.value().parameters());
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        if( y * 4 > 310 && testFloatingBiomes.GetNoise(x / 4f, z / 4f) > 0) {
            return parameters().values().get(2).getSecond();
        }

        return getTestBiome(y, 60);
    }

    private Holder<Biome> getTestBiome(int y, int caveCeiling) {
        var index = y * 4 > caveCeiling ? 0 : 1;
        return parameters().values().get(index).getSecond();
    }
}
