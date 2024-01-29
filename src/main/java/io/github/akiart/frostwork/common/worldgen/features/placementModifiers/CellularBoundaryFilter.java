package io.github.akiart.frostwork.common.worldgen.features.placementModifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class CellularBoundaryFilter extends PlacementFilter {

    public static Codec<CellularBoundaryFilter> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.DOUBLE.fieldOf("min_threshold").forGetter(filter -> filter.minThreshold),
                    Codec.DOUBLE.fieldOf("max_threshold").forGetter(filter -> filter.maxThreshold),
                    Codec.FLOAT.fieldOf("scale").forGetter(filter -> filter.scale)
            ).apply(instance, CellularBoundaryFilter::new)
    );

    private final FastNoiseLite cellularNoise;
    final double minThreshold;
    final double maxThreshold;
    final float scale;

    public CellularBoundaryFilter(double minThreshold, double maxThreshold, float scale) {
        cellularNoise = new FastNoiseLite(0);
        cellularNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        cellularNoise.SetCellularJitter(1.32f);
        cellularNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.EuclideanSq);

        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.scale = scale;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {

        //var scale = 11.82f;
        double val = cellularNoise.GetNoise(pos.getX() * scale, pos.getY() * scale, pos.getZ() * scale);
        val *= -0.86f;

        val = Mth.map(val, -0.58f, 2.16f, 0, 1);
        val *= 1.52f;

        var mul = 2.5f;
        return val >= minThreshold * mul && val <= maxThreshold * mul;
    }

    @Override
    public PlacementModifierType<?> type() {
        return FPlacementModifierTypes.CELLULAR_BOUNDARY.get();
    }
}
