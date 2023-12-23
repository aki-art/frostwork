package io.github.akiart.frostwork.common.init;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.init.block.BlockRegistryUtil;
import io.github.akiart.frostwork.common.init.block.registrySets.MushroomBlockSet;
import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.init.block.registrySets.WoodBlockSet;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Frostwork.MOD_ID);

    // Trees
    public static final WoodBlockSet FROZEN_ELM = BlockRegistryUtil.registerWoodSet("frozen_elm", MapColor.COLOR_LIGHT_BLUE, MapColor.LAPIS, MapColor.ICE, WoodType.OAK);
    public static final WoodBlockSet ELM = BlockRegistryUtil.registerWoodSet("elm", MapColor.WOOD, MapColor.TERRACOTTA_BROWN, MapColor.COLOR_GREEN, WoodType.OAK);

    // Mushrooms
    public static final MushroomBlockSet GRIMCAP = BlockRegistryUtil.registerMushroomSet("grimcap", MapColor.WOOD, MapColor.TERRACOTTA_BROWN, MapColor.COLOR_GREEN, WoodType.OAK);

    public static final StoneBlockSet OBSIDIAN_BRICKS = BlockRegistryUtil.registerStones("obsidian_bricks", 5f, 20f, MapColor.COLOR_BLACK, true);
}
