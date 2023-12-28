package io.github.akiart.frostwork.common;

import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FTags {
    public static class Blocks {
        public static final TagKey<Block> EDELSTONE_REPLACEABLE = TagKey.create(Registries.BLOCK, new ResourceLocation(Frostwork.MOD_ID, "edelstone_replaceable"));

    }
}
