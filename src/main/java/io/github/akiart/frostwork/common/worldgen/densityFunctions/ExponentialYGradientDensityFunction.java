package io.github.akiart.frostwork.common.worldgen.densityFunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public class ExponentialYGradientDensityFunction implements DensityFunction.SimpleFunction {
    private static final MapCodec<ExponentialYGradientDensityFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(

            instance -> instance.group(
                            Codec.FLOAT.fieldOf("multiplier").forGetter(fn -> fn.multiplier),
                            Codec.FLOAT.fieldOf("offset").forGetter(fn -> fn.offset)
                    )
                    .apply(instance, ExponentialYGradientDensityFunction::new)
    );

    public static final KeyDispatchDataCodec<ExponentialYGradientDensityFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

    private final float multiplier;
    private final float offset;

    public ExponentialYGradientDensityFunction(float multiplier, float offset) {
        this.offset = offset;
        this.multiplier = multiplier;
    }

    @Override
    public double compute(FunctionContext context) {
        var y = context.blockY();
        return (multiplier * y * y) + offset;
    }

    @Override
    public double minValue() {
        return 0;
    }

    private float getYOffset(float y) {
        return (0.00003f * y * y) - 0.6f;
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
