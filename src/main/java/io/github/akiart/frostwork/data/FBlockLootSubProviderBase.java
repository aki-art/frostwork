package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.blockTypes.LayeredBlock;
import io.github.akiart.frostwork.common.block.registrySets.AbstractWoodBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.MushroomBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.WoodBlockSet;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.Set;

public abstract class FBlockLootSubProviderBase extends BlockLootSubProvider {
    protected FBlockLootSubProviderBase() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    protected void ore(Block block, Item item, float min, float max) {
        add(block, createSilkTouchDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }

protected void layeredDropSelf(DeferredBlock<? extends LayeredBlock> block) {
        add(block.get(),
                itemLike -> LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                this.applyExplosionDecay(
                                                        block.get(),
                                                        LootItem.lootTableItem(itemLike)
                                                                .apply(
                                                                        List.of(2, 3, 4, 5, 6, 7, 8),
                                                                        count -> SetItemCountFunction.setCount(ConstantValue.exactly((float) count))
                                                                                .when(
                                                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(itemLike)
                                                                                                .setProperties(
                                                                                                        StatePropertiesPredicate.Builder.properties()
                                                                                                                .hasProperty(LayeredBlock.LAYERS, count)
                                                                                                )
                                                                                )
                                                                )
                                                )
                                        )
                        ));
    }


    protected void stone(StoneBlockSet set) {
        dropSelf(set.block.get());
        dropSelf(set.slab.get());
        dropSelf(set.stairs.get());
        dropSelf(set.wall.get());

        if(set.hasRedStoneComponents())
        {
            dropSelf(set.button.get());
            dropSelf(set.pressurePlate.get());
        }
    }

    protected void tree(AbstractWoodBlockSet set) {
        dropSelf(set.planks.get());
        dropSelf(set.stairs.get());
        dropSelf(set.pressurePlate.get());
        dropSelf(set.button.get());
        dropSelf(set.door.get());
        dropSelf(set.fence.get());
        dropSelf(set.fenceGate.get());
        dropSelf(set.trapDoor.get());
        dropSelf(set.slab.get());

        Frostwork.LOGGER.debug("adding set: " + set.getName());
        if(set instanceof WoodBlockSet wood) {
            Frostwork.LOGGER.debug("WoodBlockSet");
            dropSelf(wood.log.get());
            dropSelf(wood.strippedWood.get());
            dropSelf(wood.wood.get());
            dropSelf(wood.strippedLog.get());
            Frostwork.LOGGER.debug("adding leaves: " + wood.leaves.getId());
            dropSelf(wood.sapling.get());
            add(wood.leaves.get(), createLeavesDrops(wood.leaves.get(), wood.sapling.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F));
        }
        else if(set instanceof MushroomBlockSet mushroom) {
            add(mushroom.stem.get(), createMushroomBlockDrop(mushroom.stem.get(), Items.BROWN_MUSHROOM));
            add(mushroom.strippedStem.get(), createMushroomBlockDrop(mushroom.strippedStem.get(), Items.BROWN_MUSHROOM));
            add(mushroom.cap.get(), createMushroomBlockDrop(mushroom.cap.get(), Items.BROWN_MUSHROOM));
        }
    }
}
