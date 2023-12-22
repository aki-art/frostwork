package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.common.init.block.BlockRegistryUtil;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FBlockStateProvider extends FBlockStateProviderBase{
    public FBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerStones();
        //registerWoods();
    }

//    private void registerWoods() {
//        BlockRegistryUtil.getWoods().forEach(this::woods);
//    }

    private void registerStones() {
       BlockRegistryUtil.getStones().forEach(this::stones);
       // simpleBlock(FBlocks.OBSIDIAN_BRICKS.block.get());
    }
}
