package io.github.akiart.frostwork.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.SurfaceRules;

import java.util.List;

/**
 * Custom noise settings for Fantasias noise.
 *
 * @param surfaceRule Surface rules, same as usual. The cave biomes set their base stone here.
 * @param basin_level The generation smoothly fills the bottom layer of the world with whatever stone is present, so bedrock isn't exposed. Basically cave floor.
 **/
public record FNLNoiseSettings (
        SurfaceRules.RuleSource surfaceRule,
        List<Climate.ParameterPoint> spawnTarget,
        int seaLevel,
        int basin_level,
        int caveCeilingBlendStart,
        int caveCeilingBlendEnd
) {
    public static final Codec<FNLNoiseSettings> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            SurfaceRules.RuleSource.CODEC.fieldOf("surface_rule").forGetter(FNLNoiseSettings::surfaceRule),
                            Climate.ParameterPoint.CODEC.listOf().fieldOf("spawn_target").forGetter(FNLNoiseSettings::spawnTarget),
                            Codec.INT.fieldOf("sea_level").forGetter(FNLNoiseSettings::seaLevel),
                            Codec.INT.fieldOf("basin_level").forGetter(FNLNoiseSettings::basin_level),
                            Codec.INT.fieldOf("cave_ceiling_blend_start").forGetter(FNLNoiseSettings::caveCeilingBlendStart),
                            Codec.INT.fieldOf("cave_ceiling_blend_end").forGetter(FNLNoiseSettings::caveCeilingBlendEnd)
                    )
                    .apply(instance, FNLNoiseSettings::new)
    );
}
