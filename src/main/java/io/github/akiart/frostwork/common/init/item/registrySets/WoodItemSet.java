package io.github.akiart.frostwork.common.init.item.registrySets;

import io.github.akiart.frostwork.common.init.block.registrySets.WoodBlockSet;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;

public class WoodItemSet extends AbstractWoodItemSet {
    public final DeferredItem<BlockItem> log;
    public final DeferredItem<BlockItem> strippedLog;
    public final DeferredItem<BlockItem> wood;
    public final DeferredItem<BlockItem> strippedWood;
    public final DeferredItem<BlockItem> leaves;
    public final DeferredItem<BlockItem> sapling;

    public WoodItemSet(WoodBlockSet set) {
        super(set);

        log = tryRegisterFromBlock(set.log);
        wood = tryRegisterFromBlock(set.wood);
        strippedLog = tryRegisterFromBlock(set.strippedLog);
        strippedWood = tryRegisterFromBlock(set.strippedWood);
        leaves = tryRegisterFromBlock(set.leaves);
        sapling = tryRegisterFromBlock(set.sapling);
    }
}

