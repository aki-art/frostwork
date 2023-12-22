package io.github.akiart.frostwork.common.init.item;

import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.init.item.registrySets.StoneItemSet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashSet;
import java.util.function.Supplier;

public class ItemRegistryUtil {
    public static HashSet<StoneItemSet> stones = new HashSet<>();
    //public static HashSet<AbstractWoodItemSet> woods = new HashSet<>();
    public static HashSet<DeferredItem<BlockItem>> blockItems = new HashSet<>();
    public static HashSet<DeferredItem<? extends Item>> simpleItems = new HashSet<>();
    public static HashSet<DeferredItem<? extends Item>> all = new HashSet<>();

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        DeferredItem<T> reg = FItems.ITEMS.register(name, item);
        simpleItems.add(reg);
        all.add(reg);

        return reg;
    }

//    public static WoodItemSet registerWoodItems(WoodBlockSet set) {
//        var obj = new WoodItemSet(set);
//        woods.add(obj);
//
//        return  obj;
//    }

    public static DeferredItem<BlockItem> registerFromBlock(DeferredBlock<? extends Block> parent) {
        return register(parent.getId().getPath(),
                () -> new BlockItem(parent.get(), new Item.Properties()));
    }

    public static StoneItemSet registerStoneItems(StoneBlockSet parent) {
        StoneItemSet obj = new StoneItemSet(parent);
        stones.add(obj);
        return obj;
    }

//    public static io.github.akiart.fantasia.common.initializers.item.registrySets.StoneVariants createStoneItemSet(StoneVariants parent) {
//        return new io.github.akiart.fantasia.common.initializers.item.registrySets.StoneVariants(parent);
//    }

    public static DeferredItem<BlockItem> registerSimpleBlockItem(DeferredBlock<? extends Block> block) {
        DeferredItem<BlockItem> item = registerFromBlock(block);
        blockItems.add(item);
        return item;
    }
}
