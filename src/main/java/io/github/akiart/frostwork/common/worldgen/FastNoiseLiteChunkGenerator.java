package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.terrainGen.CavityGenerator;
import io.github.akiart.frostwork.common.worldgen.terrainGen.SurfaceGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FastNoiseLiteChunkGenerator extends ChunkGenerator {

    private final FNLNoiseSettings noiseSettings;
    private final SurfaceGenerator surface;
    private final CavityGenerator cavity;
    private final int chunkCountY;
    private final int chunkHeight = 4;

    private final boolean debugStrips; // used to carve out massive strips of land for easy observation of generation.

    public static final Codec<FastNoiseLiteChunkGenerator> CODEC = RecordCodecBuilder.create(
            instance -> instance
                    .group(
                            BiomeSource.CODEC.fieldOf("biome_source").forGetter(chunkGen -> chunkGen.biomeSource),
                            Codec.BOOL.fieldOf("debug_strips").forGetter(chunkGen -> chunkGen.debugStrips),
                            FNLNoiseSettings.CODEC.fieldOf("settings").forGetter(chunkGen -> chunkGen.noiseSettings)
                    )
                    .apply(instance, instance.stable(FastNoiseLiteChunkGenerator::new))
    );

    /**
     * @param debugStrips Skip giant 150 block wide gaps along the X axis, to easily overview underground generation.
     **/
    public FastNoiseLiteChunkGenerator(BiomeSource biomeSource, Boolean debugStrips, FNLNoiseSettings fnlNoiseSettings) {
        super(biomeSource);
        this.debugStrips = debugStrips;
        this.noiseSettings = fnlNoiseSettings;
        this.chunkCountY = (448+64)/4;
        this.surface = new SurfaceGenerator(biomeSource, 0, (448+64)/4, 4, 120, 448, 1.48f);
        this.cavity = new CavityGenerator(0);
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion pLevel, long pSeed, RandomState pRandom, BiomeManager pBiomeManager, StructureManager pStructureManager, ChunkAccess pChunk, GenerationStep.Carving pStep) {

    }

    @Override
    public void buildSurface(WorldGenRegion pLevel, StructureManager pStructureManager, RandomState pRandom, ChunkAccess pChunk) {

    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion pLevel) {

    }

    @Override
    public int getGenDepth() {
        return 0;
    }



    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor pExecutor, Blender pBlender, RandomState pRandom, StructureManager pStructureManager, ChunkAccess pChunk) {

        ChunkPos chunkpos = pChunk.getPos();
        int chunkX = chunkpos.getMinBlockX();
        int chunkZ = chunkpos.getMinBlockZ();

        if(debugStrips && Math.floor(chunkX / 150f) % 2 == 0) {
            return CompletableFuture.completedFuture(pChunk);
        }

        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        Heightmap oceanHeightMap = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap surfaceHeightMap = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);

        BlockState blockstate = FBlocks.EDELSTONE.block.get().defaultBlockState();
        for (int xOffset = 0; xOffset < 16; xOffset += 1) {
            for (int zOffset = 0; zOffset < 16; zOffset += 1) {

                int x = chunkX + xOffset;
                int z = chunkZ + zOffset;

                float[] noiseColumn = new float[chunkCountY + 1];
                //surface.fillNoiseColumn(noiseColumn, x, z);
                cavity.fillNoiseColumn(noiseColumn, x, z, chunkCountY, chunkHeight);
                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(xOffset, 0, zOffset);

                double value = noiseColumn[chunkCountY];

                for (int y = surface.getMaxY(); y > 0; y--) {
                    var offsetY = y + 64;
                    double nextValue = noiseColumn[y + 1];

                    for (int ys = 0; ys < chunkHeight; ++ys) {
                        pos.setY(y * chunkHeight + ys);
                        double interpolated = Mth.clampedLerp(nextValue, value, ((float) ys / (float) chunkHeight));
                        if (interpolated > 0) {
                            pChunk.setBlockState(pos, blockstate, false);
                        }
                    }

                    value = nextValue;

                    oceanHeightMap.update(xOffset, y, zOffset, blockstate);
                    surfaceHeightMap.update(xOffset, y, zOffset, blockstate);
                }
            }
        }
        // pChunk.getHeight()
//        for(int i = 0; i < noiseSettings.seaLevel(); ++i) {
//            int j = pChunk.getMinBuildHeight() + i;
//
//            for(int k = 0; k < 16; ++k) {
//                for(int l = 0; l < 16; ++l) {
//                    pChunk.setBlockState(blockpos$mutableblockpos.set(k, j, l), blockstate, false);
//                    oceanHeightMap.update(k, j, l, blockstate);
//                    surfaceHeightMap.update(k, j, l, blockstate);
//                }
//            }
//        }

        return CompletableFuture.completedFuture(pChunk);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int pX, int pZ, Heightmap.Types pType, LevelHeightAccessor pLevel, RandomState pRandom) {
        return 0;
    }

    @Override
    public NoiseColumn getBaseColumn(int pX, int pZ, LevelHeightAccessor pHeight, RandomState pRandom) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> pInfo, RandomState pRandom, BlockPos pPos) {

    }
}
