package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.blockTypes.BulbSackBlock;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.Tendrils2DConfig;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

public class TendrilFeature extends Feature<Tendrils2DConfig> {
    private final FastNoiseLite noise;

    public TendrilFeature(Codec<Tendrils2DConfig> codec) {
        super(codec);

        noise = new FastNoiseLite(0);

        noise.SetNoiseType(FastNoiseLite.NoiseType.OpenSimplex2S);
        noise.SetFractalType(FastNoiseLite.FractalType.Ridged);
        noise.SetFractalOctaves(1);
    }

    private static final List<Direction> DIRECTIONS = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.EAST,
            Direction.WEST,
            Direction.NORTH,
            Direction.SOUTH
    );

    private static boolean isValid(WorldGenLevel level, BlockPos pos, Direction direction) {
        return level.getBlockState(pos).isFaceSturdy(level, pos, direction.getOpposite());
    }

    private static boolean isClear(WorldGenLevel level, BlockPos pos) {
        return level.getBlockState(pos).isAir() || level.getFluidState(pos).is(FluidTags.WATER);
    }

    @Override
    public boolean place(FeaturePlaceContext<Tendrils2DConfig> context) {
        var level = context.level();
        var random = context.random();
        var config = context.config();
        var frequency = 1.5f;//config.frequency();
        var min = 0.95f;
        var max = 999f;

        var mutableBlockPos = context.origin().mutable();

        noise.SetSeed((int)(level.getSeed() % Integer.MAX_VALUE));

        for(int y = 9; y < 220; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    mutableBlockPos.set(context.origin()).move(x, y, z);

                    if (!isClear(level, mutableBlockPos)) {
                        continue;
                    }

                    var biome = level.getBiome(mutableBlockPos);
                    if(!biome.is(FBiomes.Cave.GRIMCAP_GROVE))
                        continue;;

                    var num = noise.GetNoise(mutableBlockPos.getX() * frequency, mutableBlockPos.getY() * frequency, mutableBlockPos.getZ() * frequency);

                    if(num > min && num < max) {
                        for(Direction dir : DIRECTIONS) {
                            var offset = mutableBlockPos.offset(dir.getNormal());
                            if(isValid(level, offset, dir)) {
                                var state = FBlocks.BULBSACK.get().defaultBlockState()
                                        .setValue(BulbSackBlock.FACING, dir.getOpposite())
                                        .setValue(BulbSackBlock.WATERLOGGED, !level.getFluidState(mutableBlockPos).is(FluidTags.WATER))
                                        .setValue(BulbSackBlock.SACKS, random.nextIntBetweenInclusive(1, 3));

                                level.setBlock(mutableBlockPos, state, Block.UPDATE_CLIENTS);

                                break;
                            }

                    }
                        //config.feature().value().place(level, context.chunkGenerator(), random, mutableBlockPos);
                        //level.setBlock(mutableBlockPos, Blocks.SHROOMLIGHT.defaultBlockState(), 2);
                    }
                }
            }
        }

        return true;
    }
}
