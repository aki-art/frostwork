package io.github.akiart.frostwork.common.worldgen;

import com.mojang.datafixers.util.Pair;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.List;

public class FDimensions {

    public static final ResourceKey<LevelStem> FANTASIA_STEM = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(Frostwork.MOD_ID, "fantasia"));
    public static final ResourceKey<Level> FANTASIA_LEVEL = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(Frostwork.MOD_ID, "frostwork"));

    public static void bootstrap(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        Climate.ParameterList<Holder<Biome>> surfaceBiomes = new Climate.ParameterList<>(
                List.of(
                    //Pair.of(Climate.parameters(0.6F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(FBiomes.Surface.FROZEN_FOREST)),
                    Pair.of(Climate.parameters(0.6F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(FBiomes.Surface.ALPINE_TUNDRA))
                   // Pair.of(Climate.parameters(-0.6F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(FBiomes.Debug.DEBUG_BLUE))
                )
        );

        Climate.ParameterList<Holder<Biome>> caveBiomes = new Climate.ParameterList<>(
                List.of(
                        //Pair.of(Climate.parameters(0.5F, 0.2F, 0.0F, 0.2F, 1F, 0.0F, 0.0F), biomeRegistry.getOrThrow(FBiomes.Cave.HIVE))
                        Pair.of(Climate.parameters(0.3F, 0.3F, 0.0F, 0.2F, 1F, 0.0F, 0.0F), biomeRegistry.getOrThrow(FBiomes.Cave.GRIMCAP_GROVE))
                        //Pair.of(Climate.parameters(0.5F, 0.7F, 0.0F, 0.2F, 1F, 0.8F, 0.0F), biomeRegistry.getOrThrow(FBiomes.Cave.GRIMCAP_GROVE))//,
                        //Pair.of(Climate.parameters(-2F, 0.2F, 0.0F, 0.2F, 1F, 0.0F, 0.0F), biomeRegistry.getOrThrow(FBiomes.Cave.FROZEN_CAVE))
                )
        );


        NoiseBasedChunkGenerator layered = new LayeredNoiseChunkGenerator(
                FantasiaBiomeSource.createFromList(surfaceBiomes, caveBiomes),
                0,
                noiseGenSettings.getOrThrow(FNoiseGenerationSettings.FANTASIA_NOISE_SETTINGS_ID));

        LevelStem stem = new LevelStem(dimensionTypes.getOrThrow(FDimensionTypes.FANTASIA_TYPE), layered);

        context.register(FANTASIA_STEM, stem);
    }
}
