package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.blockTypes.BulbSackBlock;
import io.github.akiart.frostwork.common.worldgen.FNoiseGenerationSettings;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.Tendrils2DConfig;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

public class TendrilFeature extends Feature<Tendrils2DConfig> {
    private final FastNoiseLite noise;
    //private static final float NOISE_SKIP_THRESHOLD = 0.3f;

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

    private static boolean isValid(LevelAccessor level, BlockPos pos, Direction direction) {
        return level.getBlockState(pos).isFaceSturdy(level, pos, direction.getOpposite());
    }

    private static boolean isClear(ChunkAccess level, BlockPos pos) {
        return level.getBlockState(pos).isAir() || level.getFluidState(pos).is(FluidTags.WATER);
    }

    // TelepathicGrunt
    private ChunkAccess getChunkForSpot(LevelAccessor level, ChunkAccess[] cachedSideChunks, Direction direction, BlockPos centerPos, BlockPos.MutableBlockPos reusableBlockPos) {
        reusableBlockPos.set(centerPos); // Set reusable position to center

        // Special logic for offset positions
        if (direction != null) {
            // Obtain the offset direction and position
            reusableBlockPos.move(direction);
            int directionIndex = direction.ordinal();

            // If offset position is outside center chunk. Grab side chunk using offset portion of the cache instead.
            // Otherwise, it will fall back to using center chunk if offset position does not land outside center chunk.
            if (SectionPos.blockToSectionCoord(centerPos.getX()) != SectionPos.blockToSectionCoord(reusableBlockPos.getX()) ||
                    SectionPos.blockToSectionCoord(centerPos.getZ()) != SectionPos.blockToSectionCoord(reusableBlockPos.getZ()))
            {
                // Get side offset chunk
                ChunkAccess cachedChunk = cachedSideChunks[directionIndex];

                // Cache if not yet cached
                if (cachedChunk == null) {
                    cachedChunk = level.getChunk(reusableBlockPos);
                    cachedSideChunks[directionIndex] = cachedChunk;
                }

                // Returned the cached chunk
                return cachedChunk;
            }
        }

        // Always get center chunk when no direction present
        ChunkAccess cachedChunk = cachedSideChunks[6]; // Index 6 will be center chunk

        // Cache if not yet cached
        if (cachedChunk == null) {
            cachedChunk = level.getChunk(reusableBlockPos);
            cachedSideChunks[6] = cachedChunk;
        }

        // Returned the cached chunk
        return cachedChunk;
    }

    @Override
    public boolean place(FeaturePlaceContext<Tendrils2DConfig> context) {
        var level = context.level();
        var random = context.random();
        var frequency = 1.5f;//config.frequency();
        var min = 0.95f;
        var max = 999f;
        ChunkAccess[] cachedSideChunks = new ChunkAccess[7];

        var mutableBlockPos = context.origin().mutable();
        var mutableBlockPos2 = context.origin().mutable();

        noise.SetSeed((int)(level.getSeed() % Integer.MAX_VALUE));

        for(int y = 9; y < FNoiseGenerationSettings.CAVES_TOP; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    mutableBlockPos.set(context.origin()).move(x, y, z);

                    ChunkAccess cachedChunk = getChunkForSpot(level, cachedSideChunks, null, mutableBlockPos, mutableBlockPos2);

                    if (!isClear(cachedChunk, mutableBlockPos)) {
                        continue;
                    }

                    var biome = cachedChunk.getNoiseBiome(mutableBlockPos.getX() / 4, mutableBlockPos.getY() / 4, mutableBlockPos.getZ() / 4);
                    if(!biome.is(FBiomes.Cave.GRIMCAP_GROVE))
                        continue;

                    var num = noise.GetNoise(mutableBlockPos.getX() * frequency, mutableBlockPos.getY() * frequency, mutableBlockPos.getZ() * frequency);

                    if(num < (min - 0.5f)) {
                        x += 4;
                        z += 4;

                        continue;
                    }

                    if(num > min && num < max) {
                        for(Direction dir : DIRECTIONS) {
                            //var offset = mutableBlockPos.offset(dir.getNormal()).mutable();
                            var directionalChunkAccess = getChunkForSpot(level, cachedSideChunks, dir, mutableBlockPos, mutableBlockPos2);

                            if(isValid(level, mutableBlockPos2, dir)) {
                                var state = FBlocks.BULBSACK.get().defaultBlockState()
                                        .setValue(BulbSackBlock.FACING, dir.getOpposite())
                                        .setValue(BulbSackBlock.WATERLOGGED, !cachedChunk.getFluidState(mutableBlockPos).is(FluidTags.WATER))
                                        .setValue(BulbSackBlock.SACKS, random.nextIntBetweenInclusive(1, 3));

                                //level.setBlock(mutableBlockPos, state, Block.UPDATE_CLIENTS);
                                cachedChunk.setBlockState(mutableBlockPos, state, false);

                                break;
                            }

                        }
                    }
                }
            }
        }

        return true;
    }
}
