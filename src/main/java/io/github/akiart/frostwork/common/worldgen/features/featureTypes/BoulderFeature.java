package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.BlobConfig;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class BoulderFeature extends Feature<BlobConfig> {
    private Block target;
    private FastNoiseLite noise;

    public BoulderFeature(Codec<BlobConfig> codec) {
        super(codec);
        this.noise = new FastNoiseLite(0);
        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlobConfig> context) {

        var level = context.level();
        var pos = context.origin();
        var random = context.random();
        var config = context.config();

        if (!level.getBlockState(pos.below()).is(config.target))
            return false;

        return createBlob(level, pos, config, random);
    }

    private boolean createBlob(WorldGenLevel level, BlockPos pos, BlobConfig config, RandomSource random) {
        var r = config.getRadius().sample(random);
        var d = r * 2;
        var noiseScale = 1.15f;

        for(BlockPos offset : BlockPos.withinManhattan(pos, d, d, d)) {
            var n2 = noise.GetNoise(offset.getX() * noiseScale, offset.getZ() * noiseScale);
            double n = Math.abs(n2 * 4) * r ;
            n = Mth.clamp(n, 1, r);

            if (offset.closerThan(pos, n))
                setBlock(level, config, offset, random);
        }

        return true;
    }

    private void setBlock(WorldGenLevel level, BlobConfig config, BlockPos pos, RandomSource random) {
        BlockState blockstate = level.getBlockState(pos);
        if (blockstate.is(Blocks.AIR) || blockstate.is(target)) {
            level.setBlock(pos, config.block.getState(random, pos), Block.UPDATE_CLIENTS);
        }
    }
}
