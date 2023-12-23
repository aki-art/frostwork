package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.init.block.BlockRegistryUtil;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FBlockStateProvider extends FBlockStateProviderBase{
    public FBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Frostwork.LOGGER.debug("begin block gen");
        Frostwork.LOGGER.debug("wood");
        BlockRegistryUtil.getWoods().forEach(this::wood);
        Frostwork.LOGGER.debug("stones");
        BlockRegistryUtil.getStones().forEach(this::stones);
        Frostwork.LOGGER.debug("mushroom");
        BlockRegistryUtil.getMushrooms().forEach(this::mushroom);
        Frostwork.LOGGER.debug("end");

    }
}
