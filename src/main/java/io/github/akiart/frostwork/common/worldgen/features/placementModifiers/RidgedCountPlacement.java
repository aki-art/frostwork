package io.github.akiart.frostwork.common.worldgen.features.placementModifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.stream.Stream;

public class RidgedCountPlacement extends PlacementModifier
{
    private final FastNoiseLite noise;
    private final float frequency;
    private final float cutoffMin;
    private final float cutoffMax;

    public static Codec<RidgedCountPlacement> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("frequency").forGetter(placement -> placement.frequency),
                    Codec.FLOAT.fieldOf("cutoff_min").forGetter(placement -> placement.cutoffMax),
                    Codec.FLOAT.fieldOf("cutoff_max").forGetter(placement -> placement.cutoffMax)
            ).apply(instance, RidgedCountPlacement::new));

    public RidgedCountPlacement(float frequency, float cutoffMin, float cutoffMax) {
        this.cutoffMax = cutoffMax;
        this.cutoffMin = cutoffMin;
        this.frequency = frequency;

        noise = new FastNoiseLite(45345);

        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        noise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        noise.SetFractalOctaves(1);
    }

    @Override
    public @NotNull Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {

        var result = new ArrayList<BlockPos>();
        var mutablePos = new BlockPos.MutableBlockPos();

        for(int x = 0; x < 16; x++) {
            for(int y = 0; y < 220; y++) {
                for(int z = 0; z < 16; z++) {

                    var xo = pos.getX() + x;
                    var zo = pos.getZ() + z;

                    var val = noise.GetNoise(
                            xo * frequency,
                            y * frequency,
                            zo * frequency);

                    if(val > 0.7 && val < cutoffMax) {
                        mutablePos.set(xo, y, zo);
                        result.add(mutablePos);
                    }
                }
            }
        }
        //var val = noise.GetNoise(pos.getX() * frequency, pos.getY() * frequency, pos.getZ() * frequency);
        //var i = val > cutoffMin && val < cutoffMax ? 1 : 0;

        return result.stream(); //IntStream.range(0, i).mapToObj(count -> pos);
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return FPlacementModifierTypes.RIDGED_NOISE.get();
    }
}
