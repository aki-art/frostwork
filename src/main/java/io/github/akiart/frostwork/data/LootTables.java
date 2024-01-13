package io.github.akiart.frostwork.data;


import io.github.akiart.frostwork.common.FLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Map;

public class LootTables extends LootTableProvider {
    public LootTables(PackOutput output) {
        super(output, FLootTables.get(), List.of(
                new LootTableProvider.SubProviderEntry(FBlockLootSubProvider::new, LootContextParamSets.BLOCK)
                //new LootTableProvider.SubProviderEntry(ChestLootTables::new, LootContextParamSets.CHEST),
                //new LootTableProvider.SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY),
                //new LootTableProvider.SubProviderEntry(SpecialLootTables::new, LootContextParamSets.EMPTY)
        ));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {

    }
}