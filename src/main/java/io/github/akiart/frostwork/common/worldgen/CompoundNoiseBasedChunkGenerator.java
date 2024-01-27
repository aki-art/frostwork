package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class CompoundNoiseBasedChunkGenerator extends NoiseBasedChunkGenerator {

    private static final BlockState GLASS = Blocks.CYAN_STAINED_GLASS.defaultBlockState();
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
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        if (debugStrips > 0 && Math.floor(chunkX / (float)debugStrips) % 2 == 0) {
            return CompletableFuture.completedFuture(pChunk);
        }

        for (int xOffset = 0; xOffset < 16; xOffset += 4) {
            for (int yOffset = 220; yOffset < 448; yOffset += 4) {
                for (int zOffset = 0; zOffset < 16; zOffset += 4) {

                    pos.set(chunkX + xOffset, yOffset, chunkZ + zOffset);
                    var biome = biomeSource
                            .getNoiseBiome(pos.getX() / 4, pos.getY() / 4, pos.getZ() / 4, pRandom.sampler());

                    if(biome.is(FBiomes.Sky.FLOATING_MOUNTAINS)) {
                        pChunk.setBlockState(pos, GLASS, false);
                    }
                }
            }
        }

        return super.fillFromNoise(pExecutor, pBlender, pRandom, pStructureManager, pChunk);
    }
}
