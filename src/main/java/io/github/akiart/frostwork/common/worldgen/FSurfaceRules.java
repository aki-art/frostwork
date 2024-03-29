package io.github.akiart.frostwork.common.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs.*;
import io.github.akiart.frostwork.lib.FastNoiseLite;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import org.jetbrains.annotations.NotNull;

public class FSurfaceRules {
    public static final ResourceKey<Codec<? extends SurfaceRules.RuleSource>> RULES = ResourceKey.create(Registries.MATERIAL_RULE,
            new ResourceLocation(Frostwork.MOD_ID, "fantasia"));

    private static final boolean DEBUG_MODE = true;
    public static final SurfaceRules.RuleSource EDEL_STONE = defaultState(FBlocks.EDELSTONE.block.get());
    public static final SurfaceRules.RuleSource BEDROCK = defaultState(Blocks.BEDROCK);
    public static final SurfaceRules.RuleSource SANGUITE = defaultState(FBlocks.SANGUITE.block.get());
    public static final SurfaceRules.RuleSource OVERGROWN_SANGUITE = defaultState(FBlocks.OVERGROWN_SANGUITE.get());
    public static final SurfaceRules.RuleSource AQUAMIRE = defaultState(FBlocks.AQUAMIRE.block.get());
    public static final SurfaceRules.RuleSource FROZEN_DIRT = defaultState(FBlocks.FROZEN_DIRT.get());
    public static final SurfaceRules.RuleSource DRY_GRASS = defaultState(FBlocks.DRY_GRASS.get());
    public static final SurfaceRules.RuleSource VERDANT_ROCK = defaultState(FBlocks.VERDANT_ROCK.block.get());
    public static final SurfaceRules.RuleSource GRASS = defaultState(Blocks.GRASS_BLOCK);
    public static final SurfaceRules.RuleSource GRAVEL = defaultState(Blocks.GRAVEL);
    public static final SurfaceRules.RuleSource ICE = defaultState(Blocks.ICE);
    public static final SurfaceRules.RuleSource BLUE_ICE = defaultState(Blocks.BLUE_ICE);
    public static final SurfaceRules.RuleSource SNOW = defaultState(Blocks.SNOW_BLOCK);
    public static final SurfaceRules.RuleSource AZAELA_LEAVES = defaultState(Blocks.AZALEA_LEAVES);

    // testing stuff
    public static final SurfaceRules.RuleSource RED = defaultState(Blocks.RED_CONCRETE);
    public static final SurfaceRules.RuleSource BLUE = defaultState(Blocks.BLUE_CONCRETE);
    public static final SurfaceRules.RuleSource GREEN = defaultState(Blocks.GREEN_CONCRETE);
    public static final SurfaceRules.RuleSource DEEPSLATE = defaultState(Blocks.DEEPSLATE);
    public static final SurfaceRules.RuleSource MUD = defaultState(Blocks.MUD);
    public static final SurfaceRules.RuleSource TUFF = defaultState(Blocks.TUFF);
    public static final SurfaceRules.RuleSource SANDSTONE = defaultState(Blocks.SANDSTONE);
    public static final SurfaceRules.RuleSource MOSS = defaultState(Blocks.MOSS_BLOCK);
    public static final SurfaceRules.RuleSource PITH = defaultState(FBlocks.PITH.block.get());
    public static final SurfaceRules.RuleSource HONEYCOMB_BLOCK = defaultState(Blocks.HONEYCOMB_BLOCK);

    private static FastNoiseLite cellularNoise;

    public static SurfaceRules.RuleSource defaultState(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public static final ResourceKey<Codec<? extends SurfaceRules.ConditionSource>> CONDITIONS = ResourceKey.create(Registries.MATERIAL_CONDITION,
            new ResourceLocation(Frostwork.MOD_ID, "fantasia"));

    public static SurfaceRules.RuleSource frostworkSurface() {

        initNoise();


        SurfaceRules.RuleSource grimcapGrove = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(FBiomes.Cave.GRIMCAP_GROVE),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.ON_FLOOR,
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition(FNoises.ALPINE_TUNDRA_SURFACE, -0.8, 0),
                                                        defaultState(Blocks.GRASS_BLOCK)),
                                                SurfaceRules.ifTrue(
                                                        new CellularBoundaryConditionSource(0.1f, 0.2f),
                                                        defaultState(FBlocks.SOMEWHAT_OVERGROWN_SANGUITE.get())),
                                                SurfaceRules.ifTrue(
                                                        new CellularBoundaryConditionSource(1f, 99f),
                                                        defaultState(FBlocks.MILDEW.get())),
                                                defaultState(FBlocks.OVERGROWN_SANGUITE.get())
                                        )
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.UNDER_FLOOR,
                                        defaultState(FBlocks.SOMEWHAT_OVERGROWN_SANGUITE.get())),
                                defaultState(FBlocks.SANGUITE.block.get())
                        )
                )
        );

        SurfaceRules.RuleSource hive = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(FBiomes.Cave.HIVE),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        new CellularBoundaryConditionSource(0.1f, 0.2f),
                                        HONEYCOMB_BLOCK),
                                PITH
                        )
                )
        );

        // some of the wall is ice, and some of the floor is snow. the rest is edelstone
        SurfaceRules.RuleSource frozen_cavern = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(FBiomes.Cave.FROZEN_CAVE),
                            SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.ON_FLOOR,
                                                SurfaceRules.ifTrue(
                                                        SurfaceRules.noiseCondition(FNoises.ALPINE_TUNDRA_SURFACE, -99, -0.7), SNOW)
                                    ),
                                    SurfaceRules.ifTrue(
                                            SurfaceRules.noiseCondition(FNoises.FROZEN_CAVERN_ICE, -2, 2), ICE),
                                    EDEL_STONE)
                    )
        );


        SurfaceRules.RuleSource vines_test = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        new CellularBoundaryConditionSource(0.6f, 1f),
                        GREEN
                )
        );


        SurfaceRules.RuleSource test2 = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        new CellularBoundaryConditionSource(-99f, 0.1f),
                        DEEPSLATE
                ),
                SurfaceRules.ifTrue(
                        new CellularBoundaryConditionSource(0.1f, 0.2f),
                        TUFF
                ) ,
                SurfaceRules.ifTrue(
                        new CellularBoundaryConditionSource(0.2f,0.23f),
                        MUD
                ),
                SurfaceRules.ifTrue(
                        new CellularBoundaryConditionSource(0.23f, 99f),
                        SANDSTONE
                )
        );

        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder
                .add(SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                        BEDROCK)
                )
                .add(SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("acid_flood", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(12)),
                        SurfaceRules.ifTrue(
                                SurfaceRules.hole(),
                                defaultState(FBlocks.ACID.get())
                        ))
                )
                .add(
                        AlpineTundraConfig.createSurfaceRules(),
                        frozen_cavern,
                        VerdantGladeConfig.createSurfaceRules(),
                        hive,
                        grimcapGrove,
                        FrozenForestConfig.createSurfaceRules(),
                        SkySeaConfig.createSurfaceRules(),
                        SulfurSpringsConfig.createSurfaceRules()
                )
                //.add(test2);
        ;

        if (DEBUG_MODE) {
            builder.add(simpleBiomeFiller(FBiomes.Debug.DEBUG_BLUE, BLUE));
            builder.add(simpleBiomeFiller(FBiomes.Debug.DEBUG_RED, RED));
        }

        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    @NotNull
    private static SurfaceRules.RuleSource simpleBiomeFiller(ResourceKey<Biome> biome, SurfaceRules.RuleSource rule) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(biome),
                        rule
                )
        );
    }

    private static void initNoise() {
        cellularNoise = new FastNoiseLite(0);
        cellularNoise.SetNoiseType(FastNoiseLite.NoiseType.Cellular);
        cellularNoise.SetCellularJitter(1.32f);
        cellularNoise.SetCellularDistanceFunction(FastNoiseLite.CellularDistanceFunction.EuclideanSq);
    }

    private static class CellularBoundaryCondition extends SurfaceRules.LazyCondition {

        final double minThreshold;
        final double maxThreshold;

        protected CellularBoundaryCondition(SurfaceRules.Context pContext, double minThreshold, double maxThreshold) {
            super(pContext);
            var mul = 4f;
            this.minThreshold = minThreshold;
            this.maxThreshold = maxThreshold;
        }

        @Override
        protected long getContextLastUpdate() {
            return this.context.lastUpdateY;
        }

        @Override
        protected boolean compute() {
            if(cellularNoise == null)
                initNoise();

            var scale = 11.82f;
            double val = cellularNoise.GetNoise(this.context.blockX * scale, this.context.blockY * scale, this.context.blockZ * scale);
            val *= -0.86f;

            val = Mth.map(val, -0.58f, 2.16f, 0, 1);
            val *= 1.52f;

            var mul = 2.5f;
            return val >= minThreshold * mul && val <= maxThreshold * mul;
        }
    }


    // the start of some sort of vines looking surface pattern,but i need to make it extrude from the surface
    public record CellularBoundaryConditionSource(double minThreshold, double maxThreshold) implements SurfaceRules.ConditionSource {
        public static final KeyDispatchDataCodec<CellularBoundaryConditionSource> CODEC = KeyDispatchDataCodec.of(
                RecordCodecBuilder.mapCodec(
                        instance -> instance.group(
                                Codec.DOUBLE.fieldOf("min_threshold").forGetter(CellularBoundaryConditionSource::minThreshold),
                                Codec.DOUBLE.fieldOf("max_threshold").forGetter(CellularBoundaryConditionSource::maxThreshold)
                                )
                                .apply(instance, CellularBoundaryConditionSource::new)
                )
        );

        @Override
        public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
            return CODEC;
        }

        public SurfaceRules.Condition apply(SurfaceRules.Context pContext) {
            return new CellularBoundaryCondition(pContext, minThreshold, maxThreshold);
        }
    }


 //   private static SurfaceRules.ConditionSource isOnCellularBoundary()
//    public static void bootstrap(BootstapContext<Codec<? extends SurfaceRules.ConditionSource>> context) {
//        context.register(RULES, frostworkSurface());
//    }
}
