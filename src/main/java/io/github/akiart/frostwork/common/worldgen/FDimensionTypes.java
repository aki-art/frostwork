package io.github.akiart.frostwork.common.worldgen;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.client.world.FantasiaDimensionRenderInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class FDimensionTypes {

    public static final ResourceKey<DimensionType> FANTASIA_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(Frostwork.MOD_ID, "frostwork"));

    public static final int WORLD_HEIGHT = 448;

    public static void bootstrap(BootstapContext<DimensionType> context) {
        context.register(FANTASIA_TYPE, new DimensionType(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                true,
                0,
                WORLD_HEIGHT,
                WORLD_HEIGHT,
                BlockTags.INFINIBURN_OVERWORLD,
                FantasiaDimensionRenderInfo.KEY,
                0.02f,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }
}
