package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.item.FItems;
import io.github.akiart.frostwork.common.item.ItemRegistryUtil;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
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
        fromBlock(FBlocks.MALACHITE_ICE_ORE);
        fromBlock(FBlocks.WOLF_BLOCK);
        fromBlock(FBlocks.MALACHITE_BLOCK);
        fromBlock(FBlocks.FROZEN_DIRT);
        fromBlock(FBlocks.OVERGROWN_SANGUITE);
        fromBlock(FBlocks.OVERGROWTH);
        fromBlock(FBlocks.DRY_GRASS);
        carpet(FBlocks.OVERGROWTH.getId().getPath(), new ResourceLocation((getBlockTexture(FBlocks.OVERGROWN_SANGUITE.get()) + "_top")));

        fromBlock(FBlocks.GRIMCAP_GILL);

        plants();

        miscItem(FItems.TATZELWURM_SCALE);
        miscItem(FItems.TATZELWURM_ARROW);
        miscItem(FItems.BOTTLE_OF_FOAM);
        miscItem(FItems.HUNTER_ARMOR);

        fromBlock(FBlocks.HUNTER_PELT_BROWN);
        fromBlock(FBlocks.HUNTER_PELT_CREAM);
    }

    private void plants() {
        tallPlant(FBlocks.LAVENDER);
        tallPlant(FBlocks.YARROW);
        miscItem(FItems.FORGET_ME_NOW);
    }
}
