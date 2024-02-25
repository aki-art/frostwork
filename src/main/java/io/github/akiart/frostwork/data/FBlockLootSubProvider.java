package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.BlockRegistryUtil;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.item.FItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

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

        // candeloupe drops itself if silktouched, or 1-2 candelopue slices
        add(FBlocks.CANDELOUPE.get(),
                createSilkTouchDispatchTable(
                    FBlocks.CANDELOUPE.get(),
                    applyExplosionDecay(
                            FBlocks.CANDELOUPE.get(),
                            LootItem.lootTableItem(FItems.CANDELOUPE_SLICE.get())
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f)))
                                    .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                                    .apply(LimitCount.limitCount(IntRange.upperBound(4)))

                )
        ));

        layeredDropSelf(FBlocks.SOAP_INVISIBILITY);
        layeredDropSelf(FBlocks.SOAP_REGENERATION);
        layeredDropSelf(FBlocks.SOAP_LEAPING);
        layeredDropSelf(FBlocks.SOAP_SWIFTNESS);

        addMissingTemp();
    }

    // TODO
    // temporarily make all blocks no drop i didn't set yet so the game doesn't complain
    private void addMissingTemp() {
        FBlocks.BLOCKS.getEntries().forEach(block -> {
            if(!this.map.containsKey(block.get().getLootTable())) {

                // liquids must not have a loot table
                if (block == FBlocks.ACID || block == FBlocks.AIR_BUBBLE_COLUMN)
                    return;

                add(block.get(), noDrop());
                Frostwork.LOGGER.warn("No loottable set: " + block.getId());
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
