package io.github.akiart.frostwork.common.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
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
    private static final SurfaceRules.RuleSource EDEL_STONE = defaultState(FBlocks.EDELSTONE.block.get());
    private static final SurfaceRules.RuleSource BEDROCK = defaultState(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource SANGUITE = defaultState(FBlocks.SANGUITE.block.get());
    private static final SurfaceRules.RuleSource AQUAMIRE = defaultState(FBlocks.AQUAMIRE.block.get());
    private static final SurfaceRules.RuleSource FROZEN_DIRT = defaultState(FBlocks.FROZEN_DIRT.get());
    private static final SurfaceRules.RuleSource DRY_GRASS = defaultState(FBlocks.DRY_GRASS.get());
    private static final SurfaceRules.RuleSource AZAELA_LEAVES = defaultState(Blocks.AZALEA_LEAVES);

    // testing stuff
    private static final SurfaceRules.RuleSource RED = defaultState(Blocks.RED_CONCRETE);
    private static final SurfaceRules.RuleSource BLUE = defaultState(Blocks.BLUE_CONCRETE);
    private static final SurfaceRules.RuleSource GREEN = defaultState(Blocks.GREEN_CONCRETE);
    private static final SurfaceRules.RuleSource DEEPSLATE = defaultState(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource MUD = defaultState(Blocks.PACKED_MUD);
    private static final SurfaceRules.RuleSource TUFF = defaultState(Blocks.TUFF);
    private static final SurfaceRules.RuleSource SANDSTONE = defaultState(Blocks.SANDSTONE);

    private static FastNoiseLite cellularNoise;

    private static SurfaceRules.RuleSource defaultState(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public static final ResourceKey<Codec<? extends SurfaceRules.ConditionSource>> CONDITIONS = ResourceKey.create(Registries.MATERIAL_CONDITION,
            new ResourceLocation(Frostwork.MOD_ID, "fantasia"));

    public static SurfaceRules.RuleSource frostworkSurface() {

        initNoise();

        SurfaceRules.RuleSource tundra = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(FBiomes.ALPINE_TUNDRA),
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                DRY_GRASS)
                )
        );

        SurfaceRules.RuleSource frozen_cavern = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(FBiomes.FROZEN_CAVE),
                        EDEL_STONE
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
                .add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK))
                .add(tundra)
                .add(frozen_cavern)
                //.add(test2);
        ;

        if (DEBUG_MODE) {
            builder.add(simpleBiomeFiller(FBiomes.DEBUG_BLUE, BLUE));
            builder.add(simpleBiomeFiller(FBiomes.DEBUG_RED, RED));
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
