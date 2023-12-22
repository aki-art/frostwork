package io.github.akiart.frostwork.common.init.item.registrySets;

import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.init.item.ItemRegistryUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class StoneItemSet {
    private final StoneBlockSet stone;

    public final DeferredItem<BlockItem> block;
    public final DeferredItem<BlockItem> stairs;
    public final DeferredItem<BlockItem> wall;
    public final DeferredItem<BlockItem> slab;
    public final DeferredItem<BlockItem> pressure_plate;
    public final DeferredItem<BlockItem> button;

    public StoneItemSet(StoneBlockSet stone) {
        this.stone = stone;

        block = ItemRegistryUtil.registerFromBlock(stone.block);
        stairs = ItemRegistryUtil.registerFromBlock(stone.stairs);
        wall = ItemRegistryUtil.registerFromBlock(stone.wall);
        slab = ItemRegistryUtil.registerFromBlock(stone.slab);

        pressure_plate = registerOptional(stone.pressurePlate);
        button = registerOptional(stone.button);
    }

    public String getName() {
        return stone.getName();
    }

    public StoneBlockSet getStoneRegistryObject() {
        return stone;
    }

    public boolean hadRedstoneComponents() {
        return stone.hasRedStoneComponents();
    }

    private <T extends Block> DeferredItem<BlockItem> registerOptional(DeferredBlock<T> block) {
        return block != null ? ItemRegistryUtil.registerFromBlock(block) : null;
    }
}
