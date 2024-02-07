package io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs;

import io.github.akiart.frostwork.common.worldgen.features.FPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public abstract class BaseBiomeConfig {
    private final BootstapContext<Biome> context;
    private final BiomeGenerationSettings.Builder biomeBuilder;
    private final MobSpawnSettings.Builder spawnBuilder;

    public BaseBiomeConfig(BootstapContext<Biome> context) {
        this.context = context;
        this.biomeBuilder = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        this.spawnBuilder = new MobSpawnSettings.Builder();
    }

    public Biome create() {
        return configure(biomeBuilder, spawnBuilder);
    }

    protected abstract Biome configure(BiomeGenerationSettings.Builder biomeBuilder, MobSpawnSettings.Builder spawnBuilder);

    public void addCommonSurfaceFeatures(BiomeGenerationSettings.Builder builder)  {
        builder
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FPlacedFeatures.Ores.MARLSTONE_WOLFRAMITE_ORE)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FPlacedFeatures.Ores.MARLSTONE_BURIED_OBJECT)
                .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FPlacedFeatures.Ores.MARLSTONE_COAL_ORE);
    }
}
