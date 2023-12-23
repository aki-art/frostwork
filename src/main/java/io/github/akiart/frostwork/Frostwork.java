package io.github.akiart.frostwork;

import io.github.akiart.frostwork.common.init.FCreativeModeTabs;
import io.github.akiart.frostwork.common.init.FBlocks;
import io.github.akiart.frostwork.common.init.FItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Frostwork.MOD_ID)
public class Frostwork {
    public static final String MOD_ID = "frostwork";
    public static final Logger LOGGER = LogManager.getLogger();

    public Frostwork(IEventBus modEventBus) {
        initRegistry(modEventBus);
    }

    private static void initRegistry(IEventBus modEventBus) {
        FBlocks.BLOCKS.register(modEventBus);
        FItems.ITEMS.register(modEventBus);
        FCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
    }
}
