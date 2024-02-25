package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.worldgen.biome.FBiomes;
import io.github.akiart.frostwork.common.worldgen.features.placedFeatures.SulfurSpringsPlacements;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class SulfurSpringsConfig extends BaseBiomeConfig{

    public SulfurSpringsConfig(BootstapContext<Biome> context) {
        super(context, true);
    }

    @Override
    protected Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder) {

        biomeBuilder
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, SulfurSpringsPlacements.PONDS);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x2fe0bd)
                        .waterFogColor(0x2fe0bd)
                        .skyColor(0xe9c136)
                        .grassColorOverride(13350267)
                        .foliageColorOverride(3962923)
                        .fogColor(9543081)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        //.backgroundMusic(Musics.createGameMusic(ModSounds.BAR_BRAWL.getHolder().get()))
                        .build())
                .build();
    }

    public static SurfaceRules.RuleSource createSurfaceRules()
    {
        return SurfaceRules.ifTrue(
                SurfaceRules.isBiome(FBiomes.Surface.SULFUR_SPRINGS),
                SurfaceRules.sequence(
                        SurfaceRules.ifTrue(
                                SurfaceRules.noiseCondition(Noises.ORE_VEIN_A, 0),
                                state(Blocks.RED_TERRACOTTA)),
                        state(Blocks.YELLOW_TERRACOTTA)
                )
        );
    }
}
