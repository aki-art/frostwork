package io.github.akiart.frostwork.common.init.item;

import io.github.akiart.frostwork.common.init.FItems;
import io.github.akiart.frostwork.common.init.block.registrySets.MushroomBlockSet;
import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.init.block.registrySets.WoodBlockSet;
import io.github.akiart.frostwork.common.init.item.registrySets.AbstractWoodItemSet;
import io.github.akiart.frostwork.common.init.item.registrySets.MushroomItemSet;
import io.github.akiart.frostwork.common.init.item.registrySets.StoneItemSet;
import io.github.akiart.frostwork.common.init.item.registrySets.WoodItemSet;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashSet;
import java.util.function.Supplier;

public class ItemRegistryUtil {
    public static HashSet<StoneItemSet> stones = new HashSet<>();
    public static HashSet<AbstractWoodItemSet> woods = new HashSet<>();

    public static HashSet<DeferredItem<? extends Item>> simpleItems = new HashSet<>();
    public static HashSet<DeferredItem<? extends Item>> all = new HashSet<>();

    public static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        DeferredItem<T> reg = FItems.ITEMS.register(name, item);
        simpleItems.add(reg);
        all.add(reg);

        return reg;
    }

    public static WoodItemSet registerWoodItems(WoodBlockSet set) {
        var obj = new WoodItemSet(set);
        woods.add(obj);

        return  obj;
    }

    public static DeferredItem<BlockItem> registerFromBlock(DeferredBlock<? extends Block> parent) {
        var item = FItems.ITEMS.registerSimpleBlockItem(parent);
        all.add(item);

        return item;
    }

    public static StoneItemSet registerStoneItems(StoneBlockSet parent) {
        StoneItemSet obj = new StoneItemSet(parent);
        stones.add(obj);
        return obj;
    }

    public static MushroomItemSet registerMushroomItems(MushroomBlockSet parent) {
        MushroomItemSet obj = new MushroomItemSet(parent);
        woods.add(obj);
        return obj;
    }
}
