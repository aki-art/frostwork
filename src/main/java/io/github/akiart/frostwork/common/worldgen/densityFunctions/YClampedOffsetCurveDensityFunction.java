package io.github.akiart.frostwork.common.worldgen.densityFunctions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.jetbrains.annotations.NotNull;

/** Similar to yClampedGradient, but instead of linear, this one makes the caves open up much more suddenly
 @param offset increases the caves, by shifting the cave walls. bigger values will lead to bigger caves globally
 @param curveFactor changes how steep the curve is. smaller number less difference.
 */
public record YClampedOffsetCurveDensityFunction(double curveFactor, double offset) implements DensityFunction.SimpleFunction {
    private static final MapCodec<YClampedOffsetCurveDensityFunction> DATA_CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.doubleRange(0, 1).fieldOf("multiplier").forGetter(YClampedOffsetCurveDensityFunction::curveFactor),
                            Codec.doubleRange(-1, 1).fieldOf("offset").forGetter(YClampedOffsetCurveDensityFunction::offset)
                    )
                    .apply(instance, YClampedOffsetCurveDensityFunction::new)
    );

    private double getYOffset(float y) {
        var multiplier = 0.00001f;
        var offset = 0.3f;
        return (multiplier * y * y) - offset;
    }

    public static final KeyDispatchDataCodec<YClampedOffsetCurveDensityFunction> CODEC = KeyDispatchDataCodec.of(DATA_CODEC);

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