package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import org.jetbrains.annotations.NotNull;

public class FDensityFunctions {

     /** Similar to yClampedGradient, but instead of linear, this one makes the caves open up much more suddenly
     @param offset increases the caves, by shifting the cave walls. bigger values will lead to bigger caves globally
     @param curveFactor changes how steep the curve is. smaller number less difference.
     */
    public static record YClampedOffsetCurve(double curveFactor, double offset) implements DensityFunction.SimpleFunction {
        private static final MapCodec<YClampedOffsetCurve> DATA_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.doubleRange(0, 1).fieldOf("multiplier").forGetter(YClampedOffsetCurve::curveFactor),
                                Codec.doubleRange(-1, 1).fieldOf("offset").forGetter(YClampedOffsetCurve::offset)
                        )
                        .apply(instance, YClampedOffsetCurve::new)
        );

        private double getYOffset(float y) {
            var multiplier = 0.00003f;
            var offset = 0.4f;
            return (multiplier * y * y) - offset;
        }

        public static final KeyDispatchDataCodec<YClampedOffsetCurve> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

        @Override
        public double compute(DensityFunction.FunctionContext context) {
            return getYOffset(context.blockY());
        }

        @Override
        public double minValue() {
            return getYOffset(0);
        }

        @Override
        public double maxValue() {
            return getYOffset(512);
        }

        @Override
        public @NotNull KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    public static final class WarpedSimplexDensityFunction implements DensityFunction.SimpleFunction {
        public static final KeyDispatchDataCodec<WarpedSimplexDensityFunction> CODEC = KeyDispatchDataCodec.of(
                MapCodec.unit(new WarpedSimplexDensityFunction(0L))
        );

        private final FastNoiseLite cavityNoise;

        public WarpedSimplexDensityFunction(long pSeed) {
            RandomSource randomsource = new LegacyRandomSource(pSeed);
            randomsource.consumeCount(17292);

            cavityNoise = new FastNoiseLite((int)(pSeed % Integer.MAX_VALUE));
            cavityNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
            cavityNoise.SetFrequency(0.010f);
            cavityNoise.SetFractalType(FastNoiseLite.FractalType.Ridged);
            cavityNoise.SetFractalGain(-0.38f);
            cavityNoise.SetFractalOctaves(3);
        }

        @Override
        public double compute(DensityFunction.FunctionContext pContext) {
            return getCavityNoise(pContext.blockX(), pContext.blockY(), pContext.blockZ());
        }

        private float getCavityNoise(int x, int y, int z) {
            float scale = 1f;
            float val = cavityNoise.GetNoise(x / scale, (y * 1.66f) / scale, z / scale);
            val *= 1.34f;

            return val;
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
}
