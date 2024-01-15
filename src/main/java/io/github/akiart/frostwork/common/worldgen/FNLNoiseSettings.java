package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.SurfaceRules;

import java.util.List;

public record FNLNoiseSettings (
        SurfaceRules.RuleSource surfaceRule,
        List<Climate.ParameterPoint> spawnTarget,
        int seaLevel
) {
    public static final Codec<FNLNoiseSettings> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            SurfaceRules.RuleSource.CODEC.fieldOf("surface_rule").forGetter(FNLNoiseSettings::surfaceRule),
                            Climate.ParameterPoint.CODEC.listOf().fieldOf("spawn_target").forGetter(FNLNoiseSettings::spawnTarget),
                            Codec.INT.fieldOf("sea_level").forGetter(FNLNoiseSettings::seaLevel)
                    )
                    .apply(instance, FNLNoiseSettings::new)
    );
}
