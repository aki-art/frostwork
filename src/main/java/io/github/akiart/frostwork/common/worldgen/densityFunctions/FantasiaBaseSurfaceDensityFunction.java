package io.github.akiart.frostwork.common.worldgen.densityFunctions;


import com.mojang.serialization.MapCodec;
import io.github.akiart.frostwork.common.worldgen.FNoiseGenerationSettings;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.LegacyRandomSource;

public final class FantasiaBaseSurfaceDensityFunction implements DensityFunction.SimpleFunction, ISeededDensityFunction {
    public static final KeyDispatchDataCodec<FantasiaBaseSurfaceDensityFunction> CODEC = KeyDispatchDataCodec.of(
            MapCodec.unit(new FantasiaBaseSurfaceDensityFunction(0L))
    );

    FastNoiseLite surfaceNoise;
    FastNoiseLite surfaceWarpA;
    FastNoiseLite surfaceWarpB;
    int top = FNoiseGenerationSettings.CAVES_TOP;
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

    @Override
    public void setSeed(long seed) {
        int intSeed = (int)(seed % Integer.MAX_VALUE);
        surfaceNoise.SetSeed(intSeed);
        surfaceWarpA.SetSeed(intSeed);
        surfaceWarpB.SetSeed(intSeed);
    }
}