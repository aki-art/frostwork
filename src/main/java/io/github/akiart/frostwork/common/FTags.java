package io.github.akiart.frostwork.common;

import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class FTags {
    public static final class Fluids {
        public static final TagKey<Fluid> ACID = TagKey.create(Registries.FLUID, new ResourceLocation(Frostwork.MOD_ID, "acid"));
        //public static final ITag.INamedTag<Fluid> HOT_MAGMA = createFluidTag("hot_magma");
    }

    public static class Blocks {
        public static final TagKey<Block> EDELSTONE_REPLACEABLE = TagKey.create(Registries.BLOCK, new ResourceLocation(Frostwork.MOD_ID, "edelstone_replaceable"));
        public static final TagKey<Block> MOLD = TagKey.create(Registries.BLOCK, new ResourceLocation(Frostwork.MOD_ID, "mold"));
        public static final TagKey<Block> SUSTAINS_CANDELOUPE_FRUIT = TagKey.create(Registries.BLOCK, new ResourceLocation(Frostwork.MOD_ID, "sustains_candeloupe_fruit"));

    }

    public static class Items {
        public static final TagKey<Item> HUNTER_PELT = TagKey.create(Registries.ITEM, new ResourceLocation(Frostwork.MOD_ID, "hunter_pelt"));

    }
}
