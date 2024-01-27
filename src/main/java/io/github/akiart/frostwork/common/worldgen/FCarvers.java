package io.github.akiart.frostwork.common.worldgen;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;

public class FCarvers {
    public static final ResourceKey<ConfiguredWorldCarver<?>> FANTASIA_CAVE =  ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(Frostwork.MOD_ID, "fantasia_cave"));
    public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> pContext) {
        HolderGetter<Block> blocks = pContext.lookup(Registries.BLOCK);

        pContext.register(
                FANTASIA_CAVE,
                WorldCarver.CAVE
                        .configured(
                                new CaveCarverConfiguration(
                                        0.15F,
                                        UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(220)),
                                        UniformFloat.of(0.1F, 0.9F),
                                        VerticalAnchor.aboveBottom(0),
                                        CarverDebugSettings.of(false, FBlocks.FROZEN_ELM.button.get().defaultBlockState()),
                                        blocks.getOrThrow(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
                                        UniformFloat.of(1F, 1.7F),
                                        UniformFloat.of(1.2F, 1.8F),
                                        UniformFloat.of(-1.0F, -0.4F)
                                )
                        )
        );
    }
}
