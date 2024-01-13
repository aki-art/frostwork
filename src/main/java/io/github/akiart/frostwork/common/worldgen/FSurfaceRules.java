package io.github.akiart.frostwork.common.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class FSurfaceRules {
    public static final ResourceKey<Codec<? extends SurfaceRules.RuleSource>> RULES = ResourceKey.create(Registries.MATERIAL_RULE,
            new ResourceLocation(Frostwork.MOD_ID, "fantasia"));

    private static final SurfaceRules.RuleSource EDEL_STONE = defaultState(FBlocks.EDELSTONE.block.get());
    private static final SurfaceRules.RuleSource BEDROCK = defaultState(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource SANGUITE = defaultState(FBlocks.SANGUITE.block.get());
    private static final SurfaceRules.RuleSource AQUAMIRE = defaultState(FBlocks.AQUAMIRE.block.get());
    private static final SurfaceRules.RuleSource FROZEN_DIRT = defaultState(FBlocks.FROZEN_DIRT.get());
    private static final SurfaceRules.RuleSource DRY_GRASS = defaultState(FBlocks.DRY_GRASS.get());

    private static SurfaceRules.RuleSource defaultState(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public static final ResourceKey<Codec<? extends SurfaceRules.ConditionSource>> CONDITIONS = ResourceKey.create(Registries.MATERIAL_CONDITION,
            new ResourceLocation(Frostwork.MOD_ID, "fantasia"));

    public static SurfaceRules.RuleSource frostworkSurface() {
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

        ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
        builder
                .add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK))
                .add(tundra)
                .add(frozen_cavern);


        return SurfaceRules.sequence(builder.build().toArray(SurfaceRules.RuleSource[]::new));
    }

//    public static void bootstrap(BootstapContext<Codec<? extends SurfaceRules.ConditionSource>> context) {
//        context.register(RULES, frostworkSurface());
//    }
}
