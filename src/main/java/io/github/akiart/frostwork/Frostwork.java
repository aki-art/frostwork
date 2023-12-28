package io.github.akiart.frostwork;

import io.github.akiart.frostwork.common.init.FBlocks;
import io.github.akiart.frostwork.common.init.FCreativeModeTabs;
import io.github.akiart.frostwork.common.init.FItems;
import io.github.akiart.frostwork.common.init.block.BlockRegistryUtil;
import io.github.akiart.frostwork.common.worldgen.tree.FTreeGrowers;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(Frostwork.MOD_ID)
public class Frostwork {
    public static final String MOD_ID = "frostwork";
    public static final Logger LOGGER = LogManager.getLogger();

    public Frostwork(IEventBus modEventBus) {
        initRegistry(modEventBus);

        modEventBus.addListener(this::setup);
    }

    private static void initRegistry(IEventBus modEventBus) {
        FBlocks.BLOCKS.register(modEventBus);
        FItems.ITEMS.register(modEventBus);
        FCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            Map<Block, Block> stripMap = new HashMap<>(AxeItem.STRIPPABLES);
            BlockRegistryUtil.getWoods().forEach(t -> t.setStripMaps(stripMap));
            BlockRegistryUtil.getMushrooms().forEach(t -> t.setStripMaps(stripMap));
            AxeItem.STRIPPABLES = stripMap;

            FTreeGrowers.initialize();
            //FWoodType.values().forEach(WoodType::register);
        });
    }

}
