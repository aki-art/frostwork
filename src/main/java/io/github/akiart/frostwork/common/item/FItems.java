package io.github.akiart.frostwork.common.item;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.fluid.FFluids;
import io.github.akiart.frostwork.common.item.itemTypes.BottleOfFoamItem;
import io.github.akiart.frostwork.common.item.itemTypes.HunterArmorItem;
import io.github.akiart.frostwork.common.item.itemTypes.WurmArrowItem;
import io.github.akiart.frostwork.common.item.registrySets.MushroomItemSet;
import io.github.akiart.frostwork.common.item.registrySets.StoneItemSet;
import io.github.akiart.frostwork.common.item.registrySets.WoodItemSet;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Frostwork.MOD_ID);

    // Trees
    public static final WoodItemSet FROZEN_ELM = ItemRegistryUtil.registerWoodItems(FBlocks.FROZEN_ELM);
    public static final WoodItemSet ELM = ItemRegistryUtil.registerWoodItems(FBlocks.ELM);

    // Mushrooms
    public static final MushroomItemSet GRIMCAP = ItemRegistryUtil.registerMushroomItems(FBlocks.GRIMCAP);

    public static final DeferredItem<BlockItem> GRIMCAP_GILL = ItemRegistryUtil.registerFromBlock(FBlocks.GRIMCAP_GILL);

    // Stones
    public static final StoneItemSet OBSIDIAN_BRICKS = ItemRegistryUtil.registerStoneItems(FBlocks.OBSIDIAN_BRICKS);
    public static final StoneItemSet EDELSTONE = ItemRegistryUtil.registerStoneItems(FBlocks.EDELSTONE);
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

    public static final StoneItemSet POLISHED_AQUAMIRE = ItemRegistryUtil.registerStoneItems(FBlocks.POLISHED_AQUAMIRE);

    // Ores
    public static final DeferredItem<BlockItem> EDELSTONE_COAL_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.EDELSTONE_COAL_ORE);
    public static final DeferredItem<BlockItem> MALACHITE_ICE_ORE = ItemRegistryUtil.registerFromBlock(FBlocks.MALACHITE_ICE_ORE);

    // Pelts
    public static final DeferredItem<BlockItem> HUNTER_PELT_BROWN = ItemRegistryUtil.registerFromBlock(FBlocks.HUNTER_PELT_BROWN);
    public static final DeferredItem<BlockItem> HUNTER_PELT_CREAM = ItemRegistryUtil.registerFromBlock(FBlocks.HUNTER_PELT_CREAM);

    // Plants
    public static final DeferredItem<BlockItem> LAVENDER = ItemRegistryUtil.registerFromBlock(FBlocks.LAVENDER);
    public static final DeferredItem<BlockItem> YARROW = ItemRegistryUtil.registerFromBlock(FBlocks.YARROW);
    public static final DeferredItem<BlockItem> FORGET_ME_NOW = ItemRegistryUtil.registerFromBlock(FBlocks.FORGET_ME_NOW);
    public static final DeferredItem<BlockItem> BEARBERRY = ItemRegistryUtil.registerFromBlock(FBlocks.BEARBERRY);
    public static final DeferredItem<BlockItem> CANDELOUPE = ItemRegistryUtil.registerFromBlock(FBlocks.CANDELOUPE);
    public static final DeferredItem<BlockItem> BULBSACK = ItemRegistryUtil.registerFromBlock(FBlocks.BULBSACK);

    // Combat
    public static DeferredItem<WurmArrowItem> TATZELWURM_ARROW = ItemRegistryUtil.register("tatzelwurm_arrow", () -> new WurmArrowItem(new Item.Properties()));

    // Equipment
    public static DeferredItem<HunterArmorItem> HUNTER_ARMOR = ItemRegistryUtil.register("hunter_armor",
            () -> new HunterArmorItem(FArmorMaterials.HUNTER, ArmorItem.Type.CHESTPLATE, new Item.Properties()) );

    // Misc
    public static final DeferredItem<BlockItem> WOLF_BLOCK = ItemRegistryUtil.registerFromBlock(FBlocks.WOLF_BLOCK);
    public static final DeferredItem<BlockItem> FROZEN_DIRT = ItemRegistryUtil.registerFromBlock(FBlocks.FROZEN_DIRT);
    public static final DeferredItem<BlockItem> DRY_GRASS = ItemRegistryUtil.registerFromBlock(FBlocks.DRY_GRASS);
    public static final DeferredItem<BlockItem> OVERGROWN_SANGUITE = ItemRegistryUtil.registerFromBlock(FBlocks.OVERGROWN_SANGUITE);
    //public static final DeferredItem<BlockItem> FOAM = ItemRegistryUtil.registerFromBlock(FBlocks.FOAM);
    public static final DeferredItem<BlockItem> OVERGROWTH = ItemRegistryUtil.registerFromBlock(FBlocks.OVERGROWTH);
    public static final DeferredItem<BlockItem> MALACHITE_BLOCK = ItemRegistryUtil.registerFromBlock(FBlocks.MALACHITE_BLOCK);
    public static final DeferredItem<BottleOfFoamItem> BOTTLE_OF_FOAM = ItemRegistryUtil.register("bottle_of_foam", () -> new BottleOfFoamItem(FBlocks.FOAM.get(), new Item.Properties()));
    public static final DeferredItem<Item> TATZELWURM_SCALE = ItemRegistryUtil.register("tatzelwurm_scale", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ACID_BUCKET = ItemRegistryUtil.register("acid_bucket",
            () -> new BucketItem(FFluids.ACID_SOURCE, new Item.Properties()
                    .craftRemainder(Items.BUCKET)
                    .stacksTo(999)));
}
