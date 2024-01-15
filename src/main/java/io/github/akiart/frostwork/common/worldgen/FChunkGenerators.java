package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class FChunkGenerators {
    public static final ResourceKey<Codec< ? extends ChunkGenerator>> FASTNOISELITE = ResourceKey.create(Registries.CHUNK_GENERATOR,
            new ResourceLocation(Frostwork.MOD_ID, "fastnoiselite"));

    public static void bootstrap(BootstapContext<Codec< ? extends ChunkGenerator>> context) {
        context.register(FASTNOISELITE, FastNoiseLiteChunkGenerator.CODEC);
    }
}
