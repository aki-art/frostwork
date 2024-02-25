package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.FCarvers;
import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.SurfaceRules;

public abstract class BaseBiomeConfig {
    private final BootstapContext<Biome> context;
    private final BiomeGenerationSettings.Builder biomeBuilder;
    private final MobSpawnSettings.Builder spawnBuilder;
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
    public static final SurfaceRules.RuleSource DIRT = defaultState(Blocks.DIRT);
    public static final SurfaceRules.RuleSource BLUE_ICE = defaultState(Blocks.BLUE_ICE);
    public static final SurfaceRules.RuleSource SNOW = defaultState(Blocks.SNOW_BLOCK);
    public static final SurfaceRules.RuleSource AZAELA_LEAVES = defaultState(Blocks.AZALEA_LEAVES);


    public static SurfaceRules.RuleSource defaultState(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }


    public BaseBiomeConfig(BootstapContext<Biome> context, boolean isSurface) {
        this.context = context;
        this.biomeBuilder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        this.spawnBuilder = new MobSpawnSettings.Builder();

        biomeBuilder
                .addCarver(GenerationStep.Carving.AIR, FCarvers.FANTASIA_CAVE);

        if(isSurface)
            addCommonSurfaceFeatures(biomeBuilder);
    }

    public Biome create() {
        return configure(biomeBuilder, spawnBuilder);
    }

    protected abstract Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder);

    public static SurfaceRules.RuleSource state(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public void addCommonSurfaceFeatures(BiomeGenerationSettings.Builder builder)  {
        builder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FPlacedFeatures.Ores.MARLSTONE_WOLFRAMITE_ORE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FPlacedFeatures.Ores.MARLSTONE_BURIED_OBJECT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FPlacedFeatures.Ores.MARLSTONE_COAL_ORE);
    }
}
