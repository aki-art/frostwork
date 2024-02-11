package io.github.akiart.frostwork.common.worldgen.densityFunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.LegacyRandomSource;

public class CellularPlateausDensityFunction implements DensityFunction, ISeededDensityFunction {
    private final float xzScale;
    private final float yScale;
    private final FastNoiseLite noise;

    private static final MapCodec<CellularPlateausDensityFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.FLOAT.fieldOf("xz_scale").forGetter(fn -> fn.xzScale),
                            Codec.FLOAT.fieldOf("y_scale").forGetter(fn -> fn.yScale)
                    )
                    .apply(instance, CellularPlateausDensityFunction::new)
    );
    public static final KeyDispatchDataCodec<CellularPlateausDensityFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

    public CellularPlateausDensityFunction(float xzScale, float yScale) {

        RandomSource randomsource = new LegacyRandomSource(0L);
        randomsource.consumeCount(17292);

        this.xzScale = xzScale;
        this.yScale = yScale;

        this.noise = new FastNoiseLite(randomsource.nextInt());
        noise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        noise.SetCellularReturnType(FastNoiseLite.CellularReturnType.CellValue);
    }

    @Override
    public double compute(FunctionContext context) {
        return noise.GetNoise(context.blockX() * xzScale, context.blockY() * yScale, context.blockZ() * xzScale);
    }

    @Override
    public void fillArray(double[] array, ContextProvider contextProvider) {
        contextProvider.fillAllDirectly(array, this);
    }

    @Override
    public DensityFunction mapAll(Visitor visitor) {
//        try {
//            var helper = Class
//                    .forName("net.minecraft.world.level.levelgen.RandomState$1NoiseWiringHelper");
//
//            var m = helper.getDeclaredMethod("wrapNew", DensityFunction.class);
//            m.invoke(visitor, new )
//
//        } catch (ClassNotFoundException | NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
//        if(visitor instanceof RandomState.) {
//
//        }
//        else {
//            Frostwork.LOGGER.warn("unexpected noise wrapper");
//        }

        return visitor.apply(this);
    }

    @Override
    public double minValue() {
        return 0;
    }

    @Override
    public double maxValue() {
        return 1;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return CODEC;
    }

    public void setSeed(long pLevelSeed) {
        this.noise.SetSeed((int)(pLevelSeed % Integer.MAX_VALUE));
    }
}
