package io.github.akiart.frostwork.common.worldgen.densityFunctions;


import com.mojang.serialization.MapCodec;
import io.github.akiart.frostwork.FUtil;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public final class FlatSurfaceDensityFunction implements DensityFunction.SimpleFunction {
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
