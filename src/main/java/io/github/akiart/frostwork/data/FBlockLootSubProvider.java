package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.BlockRegistryUtil;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.stream.Collectors;

public class FBlockLootSubProvider extends FBlockLootSubProviderBase {

    @Override
    protected void generate() {
        BlockRegistryUtil.getWoods().forEach(this::tree);
        BlockRegistryUtil.getMushrooms().forEach(this::tree);
        BlockRegistryUtil.getStones().forEach(this::stone);
        ore(FBlocks.EDELSTONE_COAL_ORE.get(), Items.COAL, 1, 2);
        ore(FBlocks.MALACHITE_ICE_ORE.get(), Items.COAL, 1, 2); // todo
        dropSelf(FBlocks.WOLF_BLOCK.get());
        add(FBlocks.GRIMCAP_GILL.get(), createSilkTouchOnlyTable(FBlocks.GRIMCAP_GILL.get()));

        //createSilkTouchOnlyTable(FBlocks.OVERGROWN_SANGUITE.get());
       // otherWhenSilkTouch(FBlocks.OVERGROWN_SANGUITE.get(), FBlocks.SANGUITE.block.get());

        add(FBlocks.FOAM.get(), noDrop());

        dropSelf(FBlocks.HUNTER_PELT_CREAM.get());
        dropSelf(FBlocks.HUNTER_PELT_BROWN.get());

        addMissingTemp();
    }

    //TODO
    private void addMissingTemp() {
        FBlocks.BLOCKS.getEntries().forEach(block -> {
            if(!this.map.containsKey(block.getId())) {
                if(block != FBlocks.ACID) {
                    add(block.get(), noDrop());
                    Frostwork.LOGGER.warn("No loottable set: " + block.getId());
                }
            }
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FBlocks.BLOCKS.getEntries()
                .stream()
                .map(b -> (Block)b.get())
                .collect(Collectors.toList());
    }
}
