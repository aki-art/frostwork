package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.common.block.FBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class FastNoiseLiteChunkGenerator extends ChunkGenerator {

    private static final boolean DEBUG_STRIPS = true; // used to carve out massive strips of land for easy observation of generation.

    private final Holder<NoiseGeneratorSettings> settings;

    public static final Codec<FastNoiseLiteChunkGenerator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            BiomeSource.CODEC.fieldOf("biome_source").forGetter(chunkGen -> chunkGen.biomeSource),
                            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(chunkGen -> chunkGen.settings)
                    )
                    .apply(instance, instance.stable(FastNoiseLiteChunkGenerator::new))
    );

    public FastNoiseLiteChunkGenerator(BiomeSource pBiomeSource,  Holder<NoiseGeneratorSettings> settings) {
        super(pBiomeSource);
        this.settings = settings;
    }

    public FastNoiseLiteChunkGenerator(BiomeSource pBiomeSource, Function<Holder<Biome>, BiomeGenerationSettings> pGenerationSettingsGetter,  Holder<NoiseGeneratorSettings> settings) {
        super(pBiomeSource, pGenerationSettingsGetter);
        this.settings = settings;
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

        if(DEBUG_STRIPS && Math.floor(chunkX / 150f) % 2 == 0) {
            return null;
        }
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        Heightmap heightmap = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG);
        Heightmap heightmap1 = pChunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG);

        BlockState blockstate = FBlocks.EDELSTONE.block.get().defaultBlockState();

        for(int i = 0; i < pChunk.getHeight(); ++i) {
            int j = pChunk.getMinBuildHeight() + i;

            for(int k = 0; k < 16; ++k) {
                for(int l = 0; l < 16; ++l) {
                    pChunk.setBlockState(blockpos$mutableblockpos.set(k, j, l), blockstate, false);
                    heightmap.update(k, j, l, blockstate);
                    heightmap1.update(k, j, l, blockstate);
                }
            }
        }

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
        return null;
    }

    @Override
    public void addDebugScreenInfo(List<String> pInfo, RandomState pRandom, BlockPos pPos) {

    }
}
