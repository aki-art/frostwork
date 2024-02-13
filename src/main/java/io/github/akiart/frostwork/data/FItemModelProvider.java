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

        fromBlock(FBlocks.INFESTED_VELWOOD);
        fromBlock(FBlocks.FRAMED_PITH);
        fromBlock(FBlocks.EDELSTONE_COAL_ORE);
        fromBlock(FBlocks.MALACHITE_ICE_ORE);
        fromBlock(FBlocks.WOLF_BLOCK);
        fromBlock(FBlocks.MALACHITE_BLOCK);
        fromBlock(FBlocks.FROZEN_DIRT);
        fromBlock(FBlocks.OVERGROWN_SANGUITE);
        fromBlock(FBlocks.GREEN_SANGUITE);
        fromBlock(FBlocks.OVERGROWTH);
        fromBlock(FBlocks.MILDEW);
        fromBlock(FBlocks.DRY_GRASS);
        fromBlock(FBlocks.MILDEW_FUZZ);
        fromBlock(FBlocks.SOMEWHAT_OVERGROWN_SANGUITE);
        fromBlock(FBlocks.PURPLE_GRIMCAP_CAP);

        fromBlock(FBlocks.GREEN_SCLERITE);
        fromBlock(FBlocks.PURPLE_SCLERITE);
        fromBlock(FBlocks.BLUE_SCLERITE);
        fromBlock(FBlocks.BLACK_SCLERITE);

        carpet(FBlocks.OVERGROWTH.getId().getPath(), new ResourceLocation((getBlockTexture(FBlocks.OVERGROWN_SANGUITE.get()) + "_top")));

        fromBlock(FBlocks.GRIMCAP_GILL);

        plants();
        foods();

        miscItem(FItems.TATZELWURM_SCALE);
        miscItem(FItems.TATZELWURM_ARROW);
        miscItem(FItems.BOTTLE_OF_FOAM);
        miscItem(FItems.HUNTER_ARMOR);
        miscItem(FItems.ACID_BUCKET);
        miscItem(FItems.CANDELOUPE_SEEDS);

        fromBlock(FBlocks.HUNTER_PELT_BROWN);
        fromBlock(FBlocks.HUNTER_PELT_CREAM);

        fromBlock(FBlocks.MARLSTONE_COAL_ORE);

        fromBlock(FBlocks.MARLSTONE_DIAMOND_ORE);

        fromBlock(FBlocks.MARLSTONE_BISMUTH_ORE);

        fromBlock(FBlocks.MARLSTONE_REDSTONE_ORE);

        fromBlock(FBlocks.MARLSTONE_BURIED_OBJECT);
        fromBlock(FBlocks.VERDANT_BURIED_OBJECT);

        fromBlock(FBlocks.MARLSTONE_WOLFRAMITE_ORE);
        fromBlock(FBlocks.VERDANT_WOLFRAMITE_ORE);

        fromBlock(FBlocks.MARLSTONE_GOLD_ORE);
        fromBlock(FBlocks.VERDANT_GOLD_ORE);

        fromBlock(FBlocks.MARLSTONE_LAPIS_ORE);
    }

    private void foods() {
        miscItem(FItems.CANDELOUPE_SLICE);
        miscItem(FItems.DAZZLING_CANDELOUPE_SLICE);
    }

    private void plants() {
        tallPlant(FBlocks.LAVENDER);
        tallPlant(FBlocks.YARROW);
        miscItem(FItems.FORGET_ME_NOW);
        fromBlock(FBlocks.BEARBERRY);
        fromBlock(FBlocks.CANDELOUPE);
        fromBlock(FBlocks.CARVED_CANDELOUPE);
        miscItem(FItems.BULBSACK);
    }
}
