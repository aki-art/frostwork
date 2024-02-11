package io.github.akiart.frostwork.common.worldgen;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.densityFunctions.CellularPlateausDensityFunction;
import io.github.akiart.frostwork.common.worldgen.densityFunctions.ExponentialDensityFunction;
import io.github.akiart.frostwork.common.worldgen.densityFunctions.ExponentialYGradientDensityFunction;
import io.github.akiart.frostwork.common.worldgen.densityFunctions.WarpedSimplexDensityFunction;
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
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 448;
    private static final int PLATEOUS_Y = 70;
    private static final int SEA_LEVEL = 198;
    protected static final NoiseSettings FANTASIA_NOISE_SETTINGS = NoiseSettings.create(0, 448, 1, 2);
    protected static final ResourceKey<NoiseGeneratorSettings> FANTASIA_NOISE_SETTINGS_ID =  ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(Frostwork.MOD_ID, "fantasia_noise"));
    private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
    private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
    private static final ResourceKey<DensityFunction> Y = createKey("y");
    private static final ResourceKey<DensityFunction> BASE_3D_NOISE_NETHER = createKey("nether/base_3d_noise");
    private static final ResourceKey<DensityFunction> SPAGHET = createKey("spaghetti_3d_2");
    private static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("overworld/sloped_cheese");

    // using large preset, but i have much fewer mountain regions and less interesting rivers, so this works out for my normal sized biomes
    private static final ResourceKey<DensityFunction> OFFSET_LARGE = createKey("overworld_large_biomes/offset");
    private static final ResourceKey<DensityFunction> FACTOR = createKey("overworld_large_biomes/factor");
    private static final ResourceKey<DensityFunction> SPAGHETTI_2D = createKey("overworld/caves/spaghetti_2d");
    public static NoiseGeneratorSettings bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        var settings = new NoiseGeneratorSettings(
                FANTASIA_NOISE_SETTINGS,
                FBlocks.MARLSTONE.block.get().defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                //solid(context.lookup(Registries.DENSITY_FUNCTION)),
                fantasia(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE)),
                FSurfaceRules.frostworkSurface(),
                new OverworldBiomeBuilder().spawnTarget(),
                220,
                false,
                true,
                false,
                false
        );

        context.register(FANTASIA_NOISE_SETTINGS_ID, settings);

        return settings;
    }

    private static DensityFunction spaghet(HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
        return DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.SPAGHETTI_3D_1), 3.0, 3.0);
    }

    private static DensityFunction baseNoise(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {
        return new DensityFunctions.ShiftedNoise(spaghet(noiseParameters), spaghet(noiseParameters), spaghet(noiseParameters), 0.4, 2.0, new DensityFunction.NoiseHolder(noiseParameters.getOrThrow(Noises.CAVE_CHEESE)));
    }

    private static DensityFunction baseDepth(HolderGetter<DensityFunction> pDensityFunctions) {
        // roughly caps things at y~=200
        var yLevel = DensityFunctions.yClampedGradient(50, 512, 1.5, -0.8);
        return DensityFunctions.add(yLevel, getFunction(pDensityFunctions, OFFSET_LARGE));
    }
    private static DensityFunction baseDepthButLower(HolderGetter<DensityFunction> pDensityFunctions) {
        var yLevel = DensityFunctions.yClampedGradient(40, 512, 1.5, -1.5);
        return DensityFunctions.add(yLevel, getFunction(pDensityFunctions, OFFSET_LARGE));
    }

    private static DensityFunction noodle(HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
        DensityFunction densityfunction = getFunction(pDensityFunctions, Y);

        int minY = 4;
        int maxY = 448;
        DensityFunction densityfunction1 = yLimitedInterpolatable(
                densityfunction, DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.NOODLE), 1.0, 1.0), minY, maxY, -1
        );
        DensityFunction densityfunction2 = yLimitedInterpolatable(
                densityfunction, DensityFunctions.mappedNoise(pNoiseParameters.getOrThrow(Noises.NOODLE_THICKNESS), 1.0, 1.0, -0.05, -0.1), minY, maxY, 0
        );

        double scale = 8.0/3.0;

        DensityFunction densityfunction3 = yLimitedInterpolatable(
                densityfunction, DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.NOODLE_RIDGE_A), scale, scale), minY, maxY, 0
        );
        DensityFunction densityfunction4 = yLimitedInterpolatable(
                densityfunction, DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.NOODLE_RIDGE_B), scale, scale), minY, maxY, 0
        );
        DensityFunction densityfunction5 = DensityFunctions.mul(
                DensityFunctions.constant(1.5), DensityFunctions.max(densityfunction3.abs(), densityfunction4.abs())
        );
        return DensityFunctions.rangeChoice(
                densityfunction1, -1000000.0, 0.0, DensityFunctions.constant(64.0), DensityFunctions.add(densityfunction2, densityfunction5)
        );
    }

    private static DensityFunction yLimitedInterpolatable(DensityFunction p_209472_, DensityFunction p_209473_, int p_209474_, int p_209475_, int p_209476_) {
        return DensityFunctions.interpolated(
                DensityFunctions.rangeChoice(p_209472_, (double)p_209474_, (double)(p_209475_ + 1), p_209473_, DensityFunctions.constant((double)p_209476_))
        );
    }

    private static DensityFunction undergroundPlateaus(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters)
    {
        // big polygon like voronoi shapes
        var cached = DensityFunctions.flatCache(new CellularPlateausDensityFunction(0.5f, 1f));

        // make it apply to Y level, giving elevated platforms
        var y = DensityFunctions.yClampedGradient(10, PLATEOUS_Y, 0.5f, -1);
        var plateous = DensityFunctions.add(y, cached);
        //plateous = DensityFunctions.max(plateous, DensityFunctions.constant(0));

        return DensityFunctions.rangeChoice(getFunction(densityFunctions, Y), 0, PLATEOUS_Y, plateous, DensityFunctions.constant(0));
    }

    private static DensityFunction fantasiaFinalDensity(HolderGetter<DensityFunction> densityFunctions, HolderGetter<NormalNoise.NoiseParameters> noiseParameters) {

        var plateaus =
               // DensityFunctions.interpolated(
                    DensityFunctions.interpolated(
                            undergroundPlateaus(densityFunctions, noiseParameters));

        // basic cave shape in a solid world
        var baseSimplex = DensityFunctions.mul(
                DensityFunctions.add(
                    new WarpedSimplexDensityFunction(-0.38f, 3, 1, 1.66f),
                    DensityFunctions.constant(-0.4f)),
                DensityFunctions.constant(0.34f));

        var shrinkCavesNearTop = DensityFunctions.add(
                new ExponentialYGradientDensityFunction(0.00003f, -0.6f),
                baseSimplex);

        var closeCavesNearTop = DensityFunctions.add(shrinkCavesNearTop, DensityFunctions.yClampedGradient(170, 210, 0, 10));
        var clampedCaves = DensityFunctions.min(closeCavesNearTop, DensityFunctions.constant(1));

        var surfaceDepth = baseDepth(densityFunctions);
        var scrapedTop = DensityFunctions.min(surfaceDepth, clampedCaves);

        var spaghet = getFunction(densityFunctions, SPAGHETTI_2D);
        var cavedWorld = DensityFunctions.min(scrapedTop, spaghet);

        var closeFloor = DensityFunctions.add(cavedWorld, new ExponentialDensityFunction(16f, 2f));
        var plateoud = DensityFunctions.add(closeFloor, plateaus);

        return new WarpedSimplexDensityFunction(-0.38f, 3, 1, 1.66f); // DensityFunctions.interpolated(plateoud); //DensityFunctions.mul(caves, scrapeSurface));
    }

    private static NoiseRouter fantasia(
            HolderGetter<DensityFunction> pDensityFunctions, HolderGetter<NormalNoise.NoiseParameters> pNoiseParameters) {
        DensityFunction densityfunction = getFunction(pDensityFunctions, SHIFT_X);
        DensityFunction densityfunction1 = getFunction(pDensityFunctions, SHIFT_Z);
        DensityFunction temperature = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, pNoiseParameters.getOrThrow(Noises.TEMPERATURE));
        DensityFunction vegetation = DensityFunctions.shiftedNoise2d(densityfunction, densityfunction1, 0.25, pNoiseParameters.getOrThrow(Noises.VEGETATION));

        DensityFunction aquiferBarrier = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_BARRIER), 0.5);
        DensityFunction aquiferFloodedness = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67);
        DensityFunction aquiferSpread = DensityFunctions.noise(pNoiseParameters.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD), 0.7142857142857143);

        return new NoiseRouter(
                DensityFunctions.constant(-1),
                DensityFunctions.constant(-999),
                DensityFunctions.constant(-1),

                // no lava
                DensityFunctions.constant(-1),

                temperature,
                vegetation,
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                baseDepthButLower(pDensityFunctions),
                DensityFunctions.zero(),
                // actual initial slideFantasia(pDensityFunctions),
                DensityFunctions.constant(1),
                fantasiaFinalDensity(pDensityFunctions, pNoiseParameters),
                // no veins
                DensityFunctions.zero(),
                DensityFunctions.zero(),
                DensityFunctions.zero()
        );
    }

    private static DensityFunction slideFantasia(HolderGetter<DensityFunction> densityFunctions) {
        var factor = getFunction(densityFunctions, FACTOR);
        var depth = baseDepth(densityFunctions);

        DensityFunction densityfunction10 = noiseGradientDensity(DensityFunctions.cache2d(factor), depth);
        return slideOverworld(false, DensityFunctions.add(densityfunction10, DensityFunctions.constant(-0.703125)).clamp(-64.0, 64.0));
    }


    private static DensityFunction slideF(boolean pAmplified, DensityFunction pDensityFunction) {
        return slide(pDensityFunction, 0, 448, pAmplified ? 16 : 80, pAmplified ? 0 : 64, -0.078125, 0, 24, pAmplified ? 0.4 : 0.1171875);
    }

    private static DensityFunction noiseGradientDensity(DensityFunction pMinFunction, DensityFunction pMaxFunction) {
        DensityFunction densityfunction = DensityFunctions.mul(pMaxFunction, pMinFunction);
        return DensityFunctions.mul(DensityFunctions.constant(4.0), densityfunction.quarterNegative());
    }

    // -----------------------------------------

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
    private static DensityFunction slideOverworld(boolean pAmplified, DensityFunction pDensityFunction) {
        return slide(pDensityFunction, -64, 384, pAmplified ? 16 : 80, pAmplified ? 0 : 64, -0.078125, 0, 24, pAmplified ? 0.4 : 0.1171875);
    }

    private static DensityFunction slideEndLike(DensityFunction pDensityFunction, int pMinY, int pMaxY) {
        return slide(pDensityFunction, pMinY, pMaxY, 72, -184, -23.4375, 4, 32, -0.234375);
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
