package io.github.akiart.frostwork.common.worldgen;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.common.block.FBlocks;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class LayeredNoiseChunkGenerator extends NoiseBasedChunkGenerator {
    //protected final List<Layer> layers;
    private final int debugStrips; // used to carve out massive strips of land for easy observation of generation.

    public static final Codec<LayeredNoiseChunkGenerator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            //Layer.CODEC.listOf().fieldOf("layers").forGetter(generator -> generator.layers),
                            BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> generator.biomeSource),
                            Codec.INT.fieldOf("debug_strips").forGetter(generator -> generator.debugStrips),
                            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(generator -> generator.settings)
                )
                .apply(instance, instance.stable(LayeredNoiseChunkGenerator::new))
    );

    private static Aquifer.FluidPicker createFluidPicker(NoiseGeneratorSettings pSettings) {
        Aquifer.FluidStatus acid = new Aquifer.FluidStatus(12, FBlocks.ACID.get().defaultBlockState());
        int seaLevel = pSettings.seaLevel();
        Aquifer.FluidStatus water = new Aquifer.FluidStatus(seaLevel, pSettings.defaultFluid());
        Aquifer.FluidStatus air = new Aquifer.FluidStatus(DimensionType.MIN_Y * 2, Blocks.AIR.defaultBlockState());

        return (x, y, z) -> y < Math.min(10, seaLevel) ? acid : water;
    }
    public LayeredNoiseChunkGenerator(BiomeSource biomeSource, int debugStrips, Holder<NoiseGeneratorSettings> settings) {
        super(biomeSource, settings);
        //this.layers = layers;
        this.debugStrips = debugStrips;
        this.globalFluidPicker = Suppliers.memoize(() -> createFluidPicker(settings.value()));
    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, RandomState random, StructureManager structureManager, ChunkAccess chunk) {

        ChunkPos chunkpos = chunk.getPos();
        int chunkX = chunkpos.getMinBlockX();
        int chunkZ = chunkpos.getMinBlockZ();

        if (/*debugStrips > 0 && */Math.floor(chunkX / (float)150) % 2 == 0) {
            return CompletableFuture.completedFuture(chunk);
        }

        return super.fillFromNoise(executor, blender, random, structureManager, chunk);
    }

    public record Layer(int minY, int maxY, DensityFunction area, DensityFunction surfaceDisplace) {
        public static final Codec<LayeredNoiseChunkGenerator.Layer> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                Codec.INT.fieldOf("min_y").forGetter(Layer::minY),
                                Codec.INT.fieldOf("max_y").forGetter(Layer::maxY),
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("area").forGetter(Layer::area),
                                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("surface_displacement").forGetter(Layer::surfaceDisplace)
                )
                .apply(instance, instance.stable(LayeredNoiseChunkGenerator.Layer::new))
        );
    }
}
