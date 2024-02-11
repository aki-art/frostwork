package io.github.akiart.frostwork.common.worldgen.densityFunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

public record ExponentialDensityFunction(double multiplier, double offset) implements DensityFunction.SimpleFunction{
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