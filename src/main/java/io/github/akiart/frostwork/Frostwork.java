package io.github.akiart.frostwork;

import io.github.akiart.frostwork.common.init.FCreativeModeTabs;
import io.github.akiart.frostwork.common.init.block.FBlocks;
import io.github.akiart.frostwork.common.init.item.FItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Frostwork.MOD_ID)
public class Frostwork {
    public static final String MOD_ID = "frostwork";

    public Frostwork(IEventBus modEventBus) {
        FBlocks.BLOCKS.register(modEventBus);
        FItems.ITEMS.register(modEventBus);
        FCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
    }
}
