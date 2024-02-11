package io.github.akiart.frostwork.common.worldgen.densityFunctions;


import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public final class WarpedSimplexDensityFunction implements DensityFunction.SimpleFunction {
    private static final MapCodec<WarpedSimplexDensityFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.FLOAT.fieldOf("fractal_gain").forGetter(fn -> fn.fractalGain),
                            Codec.INT.fieldOf("octaves").forGetter(fn -> fn.octaves),
                            Codec.FLOAT.fieldOf("xz_scale").forGetter(fn -> fn.xzScale),
                            Codec.FLOAT.fieldOf("y_scale").forGetter(fn -> fn.yScale)
                    )
                    .apply(instance, WarpedSimplexDensityFunction::new)
    );

    public static final KeyDispatchDataCodec<WarpedSimplexDensityFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

    private final FastNoiseLite cavityNoise;
    private final float fractalGain;
    private final int octaves;
    private final float xzScale;
    private final float yScale;

    public WarpedSimplexDensityFunction(float fractalGain, int octaves, float xzScale, float yScale) {

        this.fractalGain = fractalGain;
        this.octaves = octaves;
        this.xzScale = xzScale;
        this.yScale = yScale;

        cavityNoise = new FastNoiseLite(0);
        cavityNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        cavityNoise.SetFrequency(0.010f);
        cavityNoise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        cavityNoise.SetFractalGain(fractalGain);
        cavityNoise.SetFractalOctaves(octaves);
    }

    @Override
    public double compute(DensityFunction.FunctionContext pContext) {
        return getCavityNoise(pContext.blockX(), pContext.blockY(), pContext.blockZ());
    }

    private float getCavityNoise(int x, int y, int z) {
        return cavityNoise.GetNoise(x * xzScale, y * yScale, z * xzScale);
    }

    @Override
    public double minValue() {
        return -1;
    }

    @Override
    public double maxValue() {
        return 1;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }
}