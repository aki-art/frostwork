package io.github.akiart.frostwork.common.item.registrySets;

import io.github.akiart.frostwork.common.block.registrySets.MushroomBlockSet;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;

public class MushroomItemSet extends AbstractWoodItemSet {
    public final DeferredItem<BlockItem> stem;
    public final DeferredItem<BlockItem> strippedStem;
    public final DeferredItem<BlockItem> cap;
    public final DeferredItem<BlockItem> smallMushroom;

    public MushroomItemSet(MushroomBlockSet set) {
        super(set);

        stem = tryRegisterFromBlock(set.stem);
        strippedStem = tryRegisterFromBlock(set.strippedStem);
        cap = tryRegisterFromBlock(set.cap);
        smallMushroom = tryRegisterFromBlock(set.smallMushroom);
    }
}
