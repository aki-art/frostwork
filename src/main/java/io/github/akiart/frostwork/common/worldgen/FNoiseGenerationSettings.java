package io.github.akiart.frostwork.common.worldgen;

import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class FNoiseGenerationSettings {
    private static final int MIN_Y = -64;
    private static final int MAX_Y = 448;
    private static final int SEA_LEVEL = 150;

    protected static final NoiseSettings FANTASIA_NOISE_SETTINGS = NoiseSettings.create(-64, 384, 1, 2);
    protected static final ResourceKey<NoiseGeneratorSettings> FANTASIA_NOISE_SETTINGS_ID =  ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(Frostwork.MOD_ID, "fantasia_noise"));
    private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise");

    public static NoiseGeneratorSettings bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        var settings = new NoiseGeneratorSettings(
                FANTASIA_NOISE_SETTINGS,
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                solid(context.lookup(Registries.DENSITY_FUNCTION)),
                FSurfaceRules.frostworkSurface(),
                new OverworldBiomeBuilder().spawnTarget(),
                SEA_LEVEL,
                false,
                true,
                true,
                false
        );

        context.register(FANTASIA_NOISE_SETTINGS_ID, settings);

        return settings;
    }


    protected static NoiseRouter nether(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
        return noNewCaves(pDensityFunctions, pNoiseParameters, slideNetherLike(pDensityFunctions, 0, 128));
    }


    private static ResourceKey<DensityFunction> createKey(String pLocation) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(pLocation));
    }

    private static DensityFunction slideNetherLike(HolderGetter<DensityFunction> pDensityFunctions, int pMinY, int pMaxY) {
        return slide(getFunction(pDensityFunctions, BASE_3D_NOISE_NETHER), pMinY, pMaxY, 24, 0, 0.9375, -8, 24, 2.5);
    }

    private static DensityFunction slide(
            DensityFunction pDensityFunction, int pMinY, int pMaxY, int p_224447_, int p_224448_, double p_224449_, int p_224450_, int p_224451_, double p_224452_
    ) {
        DensityFunction densityfunction1 = DensityFunctions.yClampedGradient(pMinY + pMaxY - p_224447_, pMinY + pMaxY - p_224448_, 1.0, 0.0);
        DensityFunction $$9 = DensityFunctions.lerp(densityfunction1, p_224449_, pDensityFunction);
        DensityFunction densityfunction2 = DensityFunctions.yClampedGradient(pMinY + p_224450_, pMinY + p_224451_, 0.0, 1.0);
        return DensityFunctions.lerp(densityfunction2, p_224452_, $$9);
    }

    private static DensityFunction initialDensity(DensityFunction pDensityFunction) {
        return slideEndLike(pDensityFunction, MIN_Y, MAX_Y);
    }
    private static DensityFunction slideEndLike(DensityFunction pDensityFunction, int pMinY, int pMaxY) {
        return slide(pDensityFunction, pMinY, pMaxY, 72, -184, -23.4375, 4, 32, -0.234375);
    }
    protected static NoiseRouter solid(HolderGetter<DensityFunction> pDensityFunctions) {
        DensityFunction densityfunction = DensityFunctions.cache2d(DensityFunctions.endIslands(0L));
        DensityFunction densityfunction1 = postProcess(initialDensity(getFunction(pDensityFunctions, NoiseRouterData.FACTOR)));
        return new NoiseRouter(
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                densityfunction,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                initialDensity(DensityFunctions.add(densityfunction, DensityFunctions.constant(-0.703125))),
                densityfunction1,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero()
        );
    }
    private static NoiseRouter noNewCaves(
            HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters, DensityFunction p_256378_
    ) {
        DensityFunction densityfunction = getFunction(pDensityFunctions, SHIFT_X);
        DensityFunction densityfunction1 = getFunction(pDensityFunctions, SHIFT_Z);
        DensityFunction densityfunction2 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, pNoiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction densityfunction3 = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, pNoiseParameters.getOrThrow(Noises.VEGETATION));
        DensityFunction densityfunction4 = postProcess(p_256378_);
        return new NoiseRouter(
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                densityfunction2,
                densityfunction3,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                densityfunction4,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero()
        );
    }

    private static DensityFunction postProcess(DensityFunction pDensityFunction) {
        DensityFunction densityfunction = DensityFunctions.blendDensity(pDensityFunction);
        return DensityFunctions.mul(DensityFunctions.interpolated(densityfunction), DensityFunctions.constant(0.64)).squeeze();
    }

    private static DensityFunction getFunction(HolderGetter<DensityFunction> pDensityFunctions, ResourceKey<DensityFunction> pKey) {
        return new DensityFunctions.HolderHolder(pDensityFunctions.getOrThrow(pKey));
    }

}
