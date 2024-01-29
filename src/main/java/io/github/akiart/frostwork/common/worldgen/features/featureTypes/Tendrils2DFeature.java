package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.Tendrils2DConfig;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class Tendrils2DFeature extends Feature<Tendrils2DConfig> {
    private FastNoiseLite noise;

    public Tendrils2DFeature(Codec<Tendrils2DConfig> codec) {
            super(codec);
            noise = new FastNoiseLite(0);

            noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
            noise.SetFractalType(FastNoiseLite.FractalType.Ridged);
            noise.SetFractalOctaves(1);
    }

    @Override
    public boolean place(FeaturePlaceContext<Tendrils2DConfig> context) {

        var level = context.level();
        var pos = context.origin();
        var random = context.random();
        var config = context.config();

        var size = config.radius().sample(random);
        size = Math.min(size, 16);

        var frequency = config.frequency();

        for(BlockPos offset : BlockPos.withinManhattan(pos, size, size, size)) {
            var num = noise.GetNoise(offset.getX() * frequency, offset.getY() * frequency, offset.getZ() * frequency);
            if(num > config.cutoffMin() && num < config.cutoffMax()) {
                config.feature().value().place(level, context.chunkGenerator(), random, offset);
            }
        }

        return true;
    }
}
