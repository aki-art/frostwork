package io.github.akiart.frostwork.common.item.registrySets;

import io.github.akiart.frostwork.common.block.registrySets.AbstractWoodBlockSet;
import io.github.akiart.frostwork.common.item.ItemRegistryUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public abstract class AbstractWoodItemSet {

    protected final AbstractWoodBlockSet tree;

    public final DeferredItem<BlockItem> planks;
    public final DeferredItem<BlockItem> stairs;
    public final DeferredItem<BlockItem> fence;
    public final DeferredItem<BlockItem> fenceGate;
    public final DeferredItem<BlockItem> slab;
    public final DeferredItem<BlockItem> button;
    public final DeferredItem<BlockItem> pressurePlate;
    public final DeferredItem<BlockItem> door;
    public final DeferredItem<BlockItem> trapDoor;

    public AbstractWoodItemSet(AbstractWoodBlockSet set) {
        this.tree = set;

        button = tryRegisterFromBlock(tree.button);
        door = tryRegisterFromBlock(tree.door);
        fence = tryRegisterFromBlock(tree.fence);
        fenceGate = tryRegisterFromBlock(tree.fenceGate);
        planks = tryRegisterFromBlock(tree.planks);
        pressurePlate = tryRegisterFromBlock(tree.pressurePlate);
        slab = tryRegisterFromBlock(tree.slab);
        stairs = tryRegisterFromBlock(tree.stairs);
        trapDoor = tryRegisterFromBlock(tree.trapDoor);
    }

    public AbstractWoodBlockSet getTree() {
        return tree;
    }

    protected DeferredItem<BlockItem> tryRegisterFromBlock(DeferredBlock<? extends Block> block) {
        return block != null ? ItemRegistryUtil.registerFromBlock(block) : null;
    }

    //public String getName() {
    //    return tree.getName();
    //}
}
