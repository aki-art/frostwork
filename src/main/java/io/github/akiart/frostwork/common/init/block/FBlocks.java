package io.github.akiart.frostwork.common.init.block;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

;

public class FBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Frostwork.MOD_ID);

    public static final StoneBlockSet OBSIDIAN_BRICKS = BlockRegistryUtil.registerStones("obsidian_bricks", 5f, 20f, MapColor.COLOR_BLACK, true);
}
