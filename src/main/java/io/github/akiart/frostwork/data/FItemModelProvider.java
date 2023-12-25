package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.init.FBlocks;
import io.github.akiart.frostwork.common.init.item.ItemRegistryUtil;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FItemModelProvider extends FItemModelProviderBase {
    public FItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Frostwork.LOGGER.debug("begin item gen");
        ItemRegistryUtil.stones.forEach(this::stone);
        ItemRegistryUtil.woods.forEach(this::wood);

        fromBlock(FBlocks.EDELSTONE_COAL_ORE);
        fromBlock(FBlocks.WOLF_BLOCK);
        fromBlock(FBlocks.FROZEN_DIRT);
    }
}
