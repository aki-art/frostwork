package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class CompoundNoiseBasedChunkGenerator extends NoiseBasedChunkGenerator {

    private final int debugStrips; // used to carve out massive strips of land for easy observation of generation.

    public static final Codec<CompoundNoiseBasedChunkGenerator> CODEC = RecordCodecBuilder.create(
            instance -> instance
                    .group(
                            BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> generator.biomeSource),
                            Codec.INT.fieldOf("debug_strips").forGetter(generator -> generator.debugStrips),
                            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> generator.settings)
                    )
                    .apply(instance, instance.stable(CompoundNoiseBasedChunkGenerator::new))
    );

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    public CompoundNoiseBasedChunkGenerator(BiomeSource biomeSource, int debugStrips, Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource, settings);
        this.debugStrips = debugStrips;
    }

        @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor pExecutor, Blender pBlender, RandomState pRandom, StructureManager pStructureManager, ChunkAccess pChunk) {
        ChunkPos chunkpos = pChunk.getPos();
        int chunkX = chunkpos.getMinBlockX();
        int chunkZ = chunkpos.getMinBlockZ();

        if (debugStrips > 0 && Math.floor(chunkX / (float)debugStrips) % 2 == 0) {
            return CompletableFuture.completedFuture(pChunk);
        }

        return super.fillFromNoise(pExecutor, pBlender, pRandom, pStructureManager, pChunk);
    }
}
