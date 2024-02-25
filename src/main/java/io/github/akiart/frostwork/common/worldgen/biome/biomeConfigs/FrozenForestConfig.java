package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class FrozenForestConfig extends BaseBiomeConfig{
    public FrozenForestConfig(BootstapContext<Biome> context) {
        super(context, true);
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        BiomeDefaultFeatures.addFerns(biomeBuilder);
        biomeBuilder
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FPlacedFeatures.Vegetation.FROZEN_FOREST_ELM_TREES);


        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.1f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(11326203)
                        .waterFogColor(3095919)
                        .skyColor(12306914)
                        .grassColorOverride(12638463)
                        .foliageColorOverride(12638463)
                        .fogColor(15068150)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        //.backgroundMusic(Musics.createGameMusic(ModSounds.BAR_BRAWL.getHolder().get()))
                        .build())
                .build();
    }

    public static SurfaceRules.RuleSource createSurfaceRules()
    {
        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(FBiomes.Surface.FROZEN_FOREST),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(FBlocks.FROZEN_DIRT.get().defaultBlockState().setValue(SnowyDirtBlock.SNOWY, true))
                        ),
                        SurfaceRules.ifTrue(
                                SurfaceRules.UNDER_FLOOR,
                                SurfaceRules.state(FBlocks.FROZEN_DIRT.get().defaultBlockState().setValue(SnowyDirtBlock.SNOWY, false))
                        )
                )
        );
    }
}
