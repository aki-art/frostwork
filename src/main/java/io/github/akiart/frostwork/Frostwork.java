package io.github.akiart.frostwork;

import io.github.akiart.frostwork.common.EntityEvents;
import io.github.akiart.frostwork.common.FCreativeModeTabs;
import io.github.akiart.frostwork.common.FEntityTypes;
import io.github.akiart.frostwork.common.block.BlockRegistryUtil;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.effects.FEffects;
import io.github.akiart.frostwork.common.item.FItems;
import io.github.akiart.frostwork.common.potion.FPotions;
import io.github.akiart.frostwork.common.worldgen.FantasiaBiomeSource;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.RegisterEvent;
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
        modEventBus.addListener(this::worldgenRegistryInit);

        NeoForge.EVENT_BUS.register(EntityEvents.class);
    }

    private static void initRegistry(IEventBus modEventBus) {
        FBlocks.BLOCKS.register(modEventBus);
        FItems.ITEMS.register(modEventBus);
        FEffects.EFFECTS.register(modEventBus);
        FPotions.POTIONS.register(modEventBus);
        FEntityTypes.ENTITY_TYPES.register(modEventBus);

        FCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
    }


    public void worldgenRegistryInit(RegisterEvent evt) {
        if (evt.getRegistryKey().equals(Registries.BIOME_SOURCE))
            Registry.register(BuiltInRegistries.BIOME_SOURCE, new ResourceLocation(MOD_ID, "fantasia"), FantasiaBiomeSource.CODEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            Map<Block, Block> stripMap = new HashMap<>(AxeItem.STRIPPABLES);
            BlockRegistryUtil.getWoods().forEach(t -> t.setStripMaps(stripMap));
            BlockRegistryUtil.getMushrooms().forEach(t -> t.setStripMaps(stripMap));
            AxeItem.STRIPPABLES = stripMap;
            PotionBrewing.addMix(Potions.AWKWARD, FItems.TATZELWURM_SCALE.get(), FPotions.POISON_RESISTANCE.get());
            //BrewingRecipeRegistry.addRecipe(Ingredient.of(Potions.AWKWARD))

            //FWoodType.values().forEach(WoodType::register);
        });
    }

}
