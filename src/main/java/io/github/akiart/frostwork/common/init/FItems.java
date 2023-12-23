package io.github.akiart.frostwork.common.init;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.init.item.ItemRegistryUtil;
import io.github.akiart.frostwork.common.init.item.registrySets.MushroomItemSet;
import io.github.akiart.frostwork.common.init.item.registrySets.StoneItemSet;
import io.github.akiart.frostwork.common.init.item.registrySets.WoodItemSet;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Frostwork.MOD_ID);

    // Trees
    public static final WoodItemSet FROZEN_ELM = ItemRegistryUtil.registerWoodItems(FBlocks.FROZEN_ELM);
    public static final WoodItemSet ELM = ItemRegistryUtil.registerWoodItems(FBlocks.ELM);

    // Mushrooms
    public static final MushroomItemSet GRIMCAP = ItemRegistryUtil.registerMushroomItems(FBlocks.GRIMCAP);

    public static final StoneItemSet OBSIDIAN_BRICKS = ItemRegistryUtil.registerStoneItems(FBlocks.OBSIDIAN_BRICKS);
}
