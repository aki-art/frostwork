package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.common.init.FBlocks;
import io.github.akiart.frostwork.common.init.block.BlockRegistryUtil;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FBlockStateProvider extends FBlockStateProviderBase{
    public FBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockRegistryUtil.getWoods().forEach(this::wood);
        BlockRegistryUtil.getStones().forEach(this::stones);
        BlockRegistryUtil.getMushrooms().forEach(this::mushroom);

        simpleBlock(FBlocks.EDELSTONE_COAL_ORE.get());
        simpleBlock(FBlocks.WOLF_BLOCK.get());
        snowyBlock(FBlocks.FROZEN_DIRT.get());
        overgrowth();

        overgrownSanguite();

    }

    private void overgrowth() {
        var name = FBlocks.OVERGROWN_SANGUITE.getId().getPath();

        getVariantBuilder(FBlocks.OVERGROWTH.get()).forAllStates(state -> {

            ModelFile model = models().withExistingParent(getBlockName(FBlocks.OVERGROWTH).getPath(), new ResourceLocation("minecraft:block/carpet"))
                    .texture("wool", getLocation(name + "_top"));

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    private void overgrownSanguite() {
        var name = FBlocks.OVERGROWN_SANGUITE.getId().getPath();
        getVariantBuilder(FBlocks.OVERGROWN_SANGUITE.get()).forAllStates(state -> {
            var model = models().cubeBottomTop(name,
                    getLocation(name + "_side"),
                    blockTexture(FBlocks.SANGUITE.block.get()),
                    getLocation(name + "_top"));

            return ConfiguredModel.builder().modelFile(model).build();
         });
    }
}
