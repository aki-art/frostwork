package io.github.akiart.frostwork.common.item;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.FFoods;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.fluid.FFluids;
import io.github.akiart.frostwork.common.item.itemTypes.BottleOfFoamItem;
import io.github.akiart.frostwork.common.item.itemTypes.HunterArmorItem;
import io.github.akiart.frostwork.common.item.itemTypes.LavaPotionItem;
import io.github.akiart.frostwork.common.item.itemTypes.WurmArrowItem;
import io.github.akiart.frostwork.common.item.registrySets.MushroomItemSet;
import io.github.akiart.frostwork.common.item.registrySets.StoneItemSet;
import io.github.akiart.frostwork.common.item.registrySets.WoodItemSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Frostwork.MOD_ID);
    public static class REFS {
        public static ResourceKey<Item> CANDELOUPE_SEEDS = ResourceKey.create(Registries.ITEM, new ResourceLocation(Frostwork.MOD_ID, "candelopue_seeds"));
    }

    public static DeferredItem<Item> CANDELOUPE_SLICE = ItemRegistryUtil.register("candeloupe_slice",
            () -> new Item(new Item.Properties()
                    .food(FFoods.CANDELOUPE_SLICE)));

    public static DeferredItem<Item> DAZZLING_CANDELOUPE_SLICE = ItemRegistryUtil.register("dazzling_candeloupe_slice",
            () -> new Item(new Item.Properties()
                    .food(FFoods.DAZZLING_CANDELOUPE_SLICE)));

    // Trees
    public static final WoodItemSet FROZEN_ELM = ItemRegistryUtil.registerWoodItems(FBlocks.FROZEN_ELM);
    public static final WoodItemSet ELM = ItemRegistryUtil.registerWoodItems(FBlocks.ELM);
    public static final WoodItemSet VELWOOD = ItemRegistryUtil.registerWoodItems(FBlocks.VELWOOD);
    public static final DeferredItem<BlockItem> INFESTED_VELWOOD = ItemRegistryUtil.registerFromBlock(FBlocks.INFESTED_VELWOOD);

    // Mushrooms
    public static final MushroomItemSet GRIMCAP = ItemRegistryUtil.registerMushroomItems(FBlocks.GRIMCAP);

    public static final DeferredItem<BlockItem> GRIMCAP_GILL = ItemRegistryUtil.registerFromBlock(FBlocks.GRIMCAP_GILL);
    public static final DeferredItem<BlockItem> PURPLE_GRIMCAP_CAP = ItemRegistryUtil.registerFromBlock(FBlocks.PURPLE_GRIMCAP_CAP);

    public static DeferredItem<BlockItem> POINTED_CRYSTALMUD = ItemRegistryUtil.registerFromBlock(FBlocks.POINTED_CRYSTALMUD);
    public static DeferredItem<BlockItem> ICICLE = ItemRegistryUtil.registerFromBlock(FBlocks.ICICLE);

    // Stones
    public static final StoneItemSet OBSIDIAN_BRICKS = ItemRegistryUtil.registerStoneItems(FBlocks.OBSIDIAN_BRICKS);
    public static final StoneItemSet EDELSTONE = ItemRegistryUtil.registerStoneItems(FBlocks.EDELSTONE);
    public static final StoneItemSet SOAPSTONE = ItemRegistryUtil.registerStoneItems(FBlocks.SOAPSTONE);
    public static final StoneItemSet EDELSTONE_BRICKS = ItemRegistryUtil.registerStoneItems(FBlocks.EDELSTONE_BRICKS);
    public static final StoneItemSet CRACKED_EDELSTONE_BRICKS = ItemRegistryUtil.registerStoneItems(FBlocks.CRACKED_EDELSTONE_BRICKS);
    public static final StoneItemSet POLISHED_EDELSTONE = ItemRegistryUtil.registerStoneItems(FBlocks.POLISHED_EDELSTONE);
    public static final StoneItemSet SANGUITE = ItemRegistryUtil.registerStoneItems(FBlocks.SANGUITE);
    public static final StoneItemSet POLISHED_SANGUITE = ItemRegistryUtil.registerStoneItems(FBlocks.POLISHED_SANGUITE);
    public static final StoneItemSet POLISHED_MALACHITE = ItemRegistryUtil.registerStoneItems(FBlocks.POLISHED_MALACHITE);
    public static final StoneItemSet MARLSTONE = ItemRegistryUtil.registerStoneItems(FBlocks.MARLSTONE);
    public static final StoneItemSet MARLSTON_BRICKS = ItemRegistryUtil.registerStoneItems(FBlocks.MARLSTONE_BRICKS);
    public static final StoneItemSet AQUAMIRE = ItemRegistryUtil.registerStoneItems(FBlocks.AQUAMIRE);
    public static final StoneItemSet VERDANT_ROCK = ItemRegistryUtil.registerStoneItems(FBlocks.VERDANT_ROCK);
    public static final StoneItemSet PITH = ItemRegistryUtil.registerStoneItems(FBlocks.PITH);
    public static final DeferredItem<BlockItem> FRAMED_PITH = ItemRegistryUtil.registerFromBlock(FBlocks.FRAMED_PITH);
    public static final DeferredItem<BlockItem> GREEN_SANGUITE = ItemRegistryUtil.registerFromBlock(FBlocks.GREEN_SANGUITE);

    public static final StoneItemSet POLISHED_AQUAMIRE = ItemRegistryUtil.registerStoneItems(FBlocks.POLISHED_AQUAMIRE);

    // Ores
    public static final DeferredItem<BlockItem> EDELSTONE_COAL_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.EDELSTONE_COAL_ORE);
    public static final DeferredItem<BlockItem> MARLSTONE_COAL_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.MARLSTONE_COAL_ORE);
    public static final DeferredItem<BlockItem> MARLSTONE_BISMUTH_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.MARLSTONE_BISMUTH_ORE);
    public static final DeferredItem<BlockItem> MARLSTONE_GOLD_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.MARLSTONE_GOLD_ORE);
    public static final DeferredItem<BlockItem> MARLSTONE_LAPIS_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.MARLSTONE_LAPIS_ORE);
    public static final DeferredItem<BlockItem> MARLSTONE_WOLFRAMITE_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.MARLSTONE_WOLFRAMITE_ORE);
    public static final DeferredItem<BlockItem> MARLSTONE_DIAMOND_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.MARLSTONE_DIAMOND_ORE);
    public static final DeferredItem<BlockItem> MARLSTONE_REDSTONE_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.MARLSTONE_REDSTONE_ORE);
    public static final DeferredItem<BlockItem> MARLSTONE_BURIED_OBJECT = ItemRegistryUtil.registerFromBlock(FBlocks.MARLSTONE_BURIED_OBJECT);

    public static final DeferredItem<BlockItem> VERDANT_BURIED_OBJECT = ItemRegistryUtil.registerFromBlock(FBlocks.VERDANT_BURIED_OBJECT);
    public static final DeferredItem<BlockItem> VERDANT_GOLD_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.VERDANT_GOLD_ORE);
    public static final DeferredItem<BlockItem> VERDANT_WOLFRAMITE_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.VERDANT_WOLFRAMITE_ORE);


    public static final DeferredItem<BlockItem> MALACHITE_ICE_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.MALACHITE_ICE_ORE);
    public static final DeferredItem<BlockItem> SULFUR = ItemRegistryUtil.registerFromBlock(FBlocks.SULFUR);

    // Pelts
    public static final DeferredItem<BlockItem> HUNTER_PELT_BROWN = ItemRegistryUtil.registerFromBlock(FBlocks.HUNTER_PELT_BROWN);
    public static final DeferredItem<BlockItem> HUNTER_PELT_CREAM = ItemRegistryUtil.registerFromBlock(FBlocks.HUNTER_PELT_CREAM);

    // Soaps
    public static DeferredItem<BlockItem> SOAP_INVISIBILITY = ItemRegistryUtil.registerFromBlock(FBlocks.SOAP_INVISIBILITY);
    public static DeferredItem<BlockItem> SOAP_LEAPING = ItemRegistryUtil.registerFromBlock(FBlocks.SOAP_LEAPING);
    public static DeferredItem<BlockItem> SOAP_REGENERATION = ItemRegistryUtil.registerFromBlock(FBlocks.SOAP_REGENERATION);
    public static DeferredItem<BlockItem> SOAP_SWIFTNESS = ItemRegistryUtil.registerFromBlock(FBlocks.SOAP_SWIFTNESS);

    // Plants
    public static final DeferredItem<BlockItem> LAVENDER = ItemRegistryUtil.registerFromBlock(FBlocks.LAVENDER);
    public static final DeferredItem<BlockItem> YARROW = ItemRegistryUtil.registerFromBlock(FBlocks.YARROW);
    public static final DeferredItem<BlockItem> STARBRIGHT = ItemRegistryUtil.registerFromBlock(FBlocks.STARBRIGHT);
    public static final DeferredItem<BlockItem> FORGET_ME_NOW = ItemRegistryUtil.registerFromBlock(FBlocks.FORGET_ME_NOW);
    public static final DeferredItem<BlockItem> BEARBERRY = ItemRegistryUtil.registerFromBlock(FBlocks.BEARBERRY);
    public static final DeferredItem<BlockItem> CANDELOUPE = ItemRegistryUtil.registerFromBlock(FBlocks.CANDELOUPE);
    public static final DeferredItem<BlockItem> CARVED_CANDELOUPE = ItemRegistryUtil.registerFromBlock(FBlocks.CARVED_CANDELOUPE);
    public static final DeferredItem<BlockItem> BULBSACK = ItemRegistryUtil.registerFromBlock(FBlocks.BULBSACK);
    public static final DeferredItem<BlockItem> MILDEW_FUZZ = ItemRegistryUtil.registerFromBlock(FBlocks.MILDEW_FUZZ);
    public static final DeferredItem<BlockItem> SOMEWHAT_OVERGROWN_SANGUITE = ItemRegistryUtil.registerFromBlock(FBlocks.SOMEWHAT_OVERGROWN_SANGUITE);

    // Seeds

    public static final DeferredItem<ItemNameBlockItem> CANDELOUPE_SEEDS = ItemRegistryUtil.register("candeloupe_seeds",
            () -> new ItemNameBlockItem(FBlocks.CANDELOUPE_STEM.get(), new Item.Properties()));

    // Combat
    public static DeferredItem<WurmArrowItem> TATZELWURM_ARROW = ItemRegistryUtil.register("tatzelwurm_arrow", () -> new WurmArrowItem(new Item.Properties()));

    // Equipment
    public static DeferredItem<HunterArmorItem> HUNTER_ARMOR = ItemRegistryUtil.register("hunter_armor",
            () -> new HunterArmorItem(FArmorMaterials.HUNTER, ArmorItem.Type.CHESTPLATE, new Item.Properties()) );

    // Misc
    public static final DeferredItem<BlockItem> WOLF_BLOCK = ItemRegistryUtil.registerFromBlock(FBlocks.WOLF_BLOCK);
    public static final DeferredItem<BlockItem> GREEN_SCLERITE = ItemRegistryUtil.registerFromBlock(FBlocks.GREEN_SCLERITE);
    public static final DeferredItem<BlockItem> BLUE_SCLERITE = ItemRegistryUtil.registerFromBlock(FBlocks.BLUE_SCLERITE);
    public static final DeferredItem<BlockItem> BLACK_SCLERITE = ItemRegistryUtil.registerFromBlock(FBlocks.BLACK_SCLERITE);
    public static final DeferredItem<BlockItem> PURPLE_SCLERITE = ItemRegistryUtil.registerFromBlock(FBlocks.PURPLE_SCLERITE);
    public static final DeferredItem<BlockItem> MILDEW = ItemRegistryUtil.registerFromBlock(FBlocks.MILDEW);
    public static final DeferredItem<BlockItem> FROZEN_DIRT = ItemRegistryUtil.registerFromBlock(FBlocks.FROZEN_DIRT);
    public static final DeferredItem<BlockItem> DRY_GRASS = ItemRegistryUtil.registerFromBlock(FBlocks.DRY_GRASS);
    public static final DeferredItem<BlockItem> OVERGROWN_SANGUITE = ItemRegistryUtil.registerFromBlock(FBlocks.OVERGROWN_SANGUITE);
    //public static final DeferredItem<BlockItem> FOAM = ItemRegistryUtil.registerFromBlock(FBlocks.FOAM);
    public static final DeferredItem<BlockItem> OVERGROWTH = ItemRegistryUtil.registerFromBlock(FBlocks.OVERGROWTH);
    public static final DeferredItem<BlockItem> BUBBLE_VENT = ItemRegistryUtil.registerFromBlock(FBlocks.BUBBLE_VENT);
    public static final DeferredItem<BlockItem> MALACHITE_BLOCK = ItemRegistryUtil.registerFromBlock(FBlocks.MALACHITE_BLOCK);
    public static final DeferredItem<BottleOfFoamItem> BOTTLE_OF_FOAM = ItemRegistryUtil.register("bottle_of_foam", () -> new BottleOfFoamItem(FBlocks.FOAM.get(), new Item.Properties()));
    public static final DeferredItem<Item> SAP_O_MITE_BOTTLE = ItemRegistryUtil.register("sap_o_mite_bottle", () -> new Item(new Item.Properties()));
    public static final DeferredItem<LavaPotionItem> LAVA_POTION = ItemRegistryUtil.register("lava_potion", () -> new LavaPotionItem(new Item.Properties()));
    public static final DeferredItem<Item> TATZELWURM_SCALE = ItemRegistryUtil.register("tatzelwurm_scale", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ACID_BUCKET = ItemRegistryUtil.register("acid_bucket",
            () -> new BucketItem(FFluids.ACID_SOURCE, new Item.Properties()
                    .craftRemainder(Items.BUCKET)
                    .stacksTo(999)));
}
