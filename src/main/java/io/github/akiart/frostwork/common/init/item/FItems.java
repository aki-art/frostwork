package io.github.akiart.frostwork.common.init.item;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.init.block.FBlocks;
import io.github.akiart.frostwork.common.init.item.registrySets.StoneItemSet;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Frostwork.MOD_ID);

    public static final StoneItemSet OBSIDIAN_BRICKS = ItemRegistryUtil.registerStoneItems(FBlocks.OBSIDIAN_BRICKS);
}
