package io.github.akiart.frostwork.common.worldgen.densityFunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public class BigCavesDensityFunction implements DensityFunction.SimpleFunction, ISeededDensityFunction {
    private static final MapCodec<BigCavesDensityFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.FLOAT.fieldOf("fractal_gain").forGetter(fn -> fn.fractalGain),
                            Codec.INT.fieldOf("octaves").forGetter(fn -> fn.octaves),
                            Codec.FLOAT.fieldOf("lacunarity").forGetter(fn -> fn.lacunarity),
                            Codec.FLOAT.fieldOf("xz_scale").forGetter(fn -> fn.xzScale),
                            Codec.FLOAT.fieldOf("y_scale").forGetter(fn -> fn.yScale)
                    )
                    .apply(instance, BigCavesDensityFunction::new)
    );

    public static final KeyDispatchDataCodec<BigCavesDensityFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

    public static final ResourceLocation ID = new ResourceLocation(Frostwork.MOD_ID, "big_caves");

    private final float fractalGain;
    private final int octaves;
    private final float lacunarity;
    private final float xzScale;
    private final float yScale;
    private final FastNoiseLite cavityNoise;
    //private final FastNoiseLite warpNoise;

    private FastNoiseLite.Vector3 position;

    public BigCavesDensityFunction(float fractalGain, int octaves, float lacunarity, float xzScale, float yScale) {

        this.fractalGain = fractalGain;
        this.octaves = octaves;
        this.xzScale = xzScale;
        this.yScale = yScale;
        this.lacunarity = lacunarity;

        this.cavityNoise = new FastNoiseLite(0);
        cavityNoise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
        cavityNoise.SetFractalType(FastNoiseLite.FractalType.FBm);
        cavityNoise.SetFractalGain(fractalGain);
        cavityNoise.SetFractalOctaves(octaves);
        cavityNoise.SetFractalLacunarity(lacunarity);

        cavityNoise.SetDomainWarpAmp(0.2f);
        cavityNoise.SetDomainWarpType(FastNoiseLite.DomainWarpType.OpenSimplex2);
        position = new FastNoiseLite.Vector3(0, 0, 0);
    }

    @Override
    public void setSeed(long seed) {
        int intSeed = (int)(seed % Integer.MAX_VALUE);
        cavityNoise.SetSeed(intSeed);
    }



    @Override
    public double compute(FunctionContext context) {
        position.x = context.blockX();
        position.y = context.blockY();
        position.z = context.blockZ();

        cavityNoise.DomainWarp(position);

        return cavityNoise.GetNoise(position.x, position.y, position.z);
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
