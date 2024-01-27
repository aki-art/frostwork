package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.FUtil;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import org.jetbrains.annotations.NotNull;

public class FDensityFunctions {

    public static record ExponentialDensityFunction(double multiplier, double offset) implements DensityFunction.SimpleFunction{
        private static final MapCodec<ExponentialDensityFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Codec.DOUBLE.fieldOf("multiplier").forGetter(ExponentialDensityFunction::multiplier),
                                Codec.DOUBLE.fieldOf("offset").forGetter(ExponentialDensityFunction::offset)
                        )
                        .apply(instance, ExponentialDensityFunction::new)
        );

        public static final KeyDispatchDataCodec<ExponentialDensityFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

        @Override
        public double compute(FunctionContext context) {
            return multiplier * Math.exp(-0.4f * (context.blockY() - offset));
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
            var multiplier = 0.00001f;
            var offset = 0.3f;
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

        private float getYOffset(float y) {
            return (0.00003f * y * y) - 0.6f;
        }

        private float getCavityNoise(int x, int y, int z) {
            float scale = 1f;
            float val = cavityNoise.GetNoise(x / scale, (y * 1.66f) / scale, z / scale);
            //val -= 0.4f;
            val *= 1.34f;

            val += getYOffset(y);
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
    public static final class FlatSurfaceDensityFunction implements DensityFunction.SimpleFunction {
        public static final KeyDispatchDataCodec<FlatSurfaceDensityFunction> CODEC = KeyDispatchDataCodec.of(
                MapCodec.unit(new FlatSurfaceDensityFunction(0L))
        );

        int top = 220;

        private final FastNoiseLite noise;

        public FlatSurfaceDensityFunction(long pSeed) {

            noise = new FastNoiseLite((int)(pSeed % Integer.MAX_VALUE));
            noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);

        }

        @Override
        public double compute(DensityFunction.FunctionContext pContext) {
            return getNoise(pContext.blockX(), pContext.blockY(), pContext.blockZ());
        }

        private float getNoise(int x, int y, int z) {
            float scale = 1.04f;
            float val = noise.GetNoise(x / scale, y / scale, z / scale);
            val *= FUtil.remap(val, -1, 1, 0, 0.34f);
            var bias = getYBias(y);
            //bias = Math.max(bias, 0);

            val += bias;

            return val;
        }

        private float getYBias(float y) {
            return  (y - top) * -16.9f;
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

    public static final class FantasiaBaseSurfaceDensityFunction implements DensityFunction.SimpleFunction {
        public static final KeyDispatchDataCodec<FantasiaBaseSurfaceDensityFunction> CODEC = KeyDispatchDataCodec.of(
                MapCodec.unit(new FantasiaBaseSurfaceDensityFunction(0L))
        );

        FastNoiseLite surfaceNoise;
        FastNoiseLite surfaceWarpA;
        FastNoiseLite surfaceWarpB;
        int top = 220;
        private float terrainScale = 2.3f;

        public FantasiaBaseSurfaceDensityFunction(long pSeed) {
            RandomSource randomsource = new LegacyRandomSource(pSeed);
            randomsource.consumeCount(17292);

            int seed = (int)(pSeed % Integer.MAX_VALUE);

            // This solution made more sense before 1.18 MC also switched to similar methods. But I'm sticking with this
            surfaceNoise = new FastNoiseLite(seed);
            surfaceNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
            surfaceNoise.SetFrequency(0.02f);
            surfaceNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
            surfaceNoise.SetFractalOctaves(4);

            // Warping works by sampling the noise just a little off position, leading to swoopier more interesting shapes and more overhangs.
            surfaceWarpA = new FastNoiseLite(seed);
            surfaceWarpA.SetDomainWarpAmp(0.1f);
            surfaceWarpA.SetFrequency(5.6f);
            surfaceWarpA.SetFractalType(FastNoiseLite.FractalType.DomainWarpProgressive);
            surfaceWarpA.SetFractalLacunarity(2f);
            surfaceWarpA.SetFractalGain(0.5f);
            surfaceWarpA.SetFractalOctaves(3);

            surfaceWarpB = new FastNoiseLite(seed);
            surfaceWarpB.SetDomainWarpAmp(0.36f);
            surfaceWarpB.SetFrequency(1.7f);
            surfaceWarpB.SetFractalType(FastNoiseLite.FractalType.DomainWarpProgressive);
            surfaceWarpB.SetFractalLacunarity(2f);
            surfaceWarpB.SetFractalGain(0.58f);
            surfaceWarpB.SetFractalOctaves(4);
        }

        // Used to flatten out the 3D noise to a 2D flat terrain.
        private float getYBias(float y) {
            return  - y * (0.05f / terrainScale);
        }
        private float getSurfaceValue(float x, float y, float z, float yScale, float yOffset) {
            FastNoiseLite.Vector3 warpedPosB = new FastNoiseLite.Vector3(x / terrainScale, (y / (terrainScale * yScale)), z / terrainScale);
            surfaceWarpA.DomainWarp(warpedPosB);
            return getSurfaceValueB(warpedPosB.x, warpedPosB.y, warpedPosB.z);
        }
        private float getSurfaceValueB(float x, float y, float z) {
            FastNoiseLite.Vector3 warpedPosA = new FastNoiseLite.Vector3(x, y, z);
            surfaceWarpB.DomainWarp(warpedPosA);
            return getYBias(y) + surfaceNoise.GetNoise(warpedPosA.x, warpedPosA.y, warpedPosA.z);
        }


        @Override
        public double compute(DensityFunction.FunctionContext pContext) {
            float scale = 0.5f;
            float depthOffset = top + top * 1f / 4f; // * (1f / 4f);
            return getSurfaceValue(pContext.blockX(), pContext.blockY() - depthOffset, pContext.blockZ(), 1f, 0);
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
