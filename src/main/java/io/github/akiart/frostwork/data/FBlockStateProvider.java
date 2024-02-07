package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.BlockRegistryUtil;
import io.github.akiart.frostwork.common.block.blockTypes.PeltBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.minecraft.world.level.block.*;

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
        simpleBlock(FBlocks.MALACHITE_BLOCK.get());
        mushroomBlock(FBlocks.PURPLE_GRIMCAP_CAP, getLocation("grimcap_stem_top"));
        snowyBlock(FBlocks.FROZEN_DIRT.get());
        simpleBlock(FBlocks.MILDEW.get());

        translucentSimpleBlock(FBlocks.GREEN_SCLERITE);
        translucentSimpleBlock(FBlocks.BLUE_SCLERITE);
        translucentSimpleBlock(FBlocks.PURPLE_SCLERITE);
        translucentSimpleBlock(FBlocks.BLACK_SCLERITE);

        overgrowth();
        overgrownSanguite();
        dryGrass();

        plants();
        foam(FBlocks.FOAM);

        crossBlock(FBlocks.GRIMCAP_GILL);

        partialEmissive(FBlocks.MALACHITE_ICE_ORE, "translucent");

        pelt(FBlocks.HUNTER_PELT_BROWN);
        pelt(FBlocks.HUNTER_PELT_CREAM);

        simpleBlock(FBlocks.SOMEWHAT_OVERGROWN_SANGUITE.get());

        simpleBlock(FBlocks.FRAMED_PITH.get());

        simpleBlock(FBlocks.MARLSTONE_COAL_ORE.get());

        simpleBlock(FBlocks.MARLSTONE_DIAMOND_ORE.get());

        simpleBlock(FBlocks.MARLSTONE_BISMUTH_ORE.get());

        simpleBlock(FBlocks.MARLSTONE_REDSTONE_ORE.get());

        simpleBlock(FBlocks.MARLSTONE_BURIED_OBJECT.get());
        simpleBlock(FBlocks.VERDANT_BURIED_OBJECT.get());

        simpleBlock(FBlocks.MARLSTONE_WOLFRAMITE_ORE.get());
        simpleBlock(FBlocks.VERDANT_WOLFRAMITE_ORE.get());

        simpleBlock(FBlocks.MARLSTONE_GOLD_ORE.get());
        simpleBlock(FBlocks.VERDANT_GOLD_ORE.get());

        simpleBlock(FBlocks.MARLSTONE_LAPIS_ORE.get());
    }

    private void plants() {
        tallPlant(FBlocks.LAVENDER);
        tallPlant(FBlocks.YARROW);
        //forgetMeNow();
        crossBlock(FBlocks.FORGET_ME_NOW);
        crossBlock(FBlocks.BEARBERRY);
        candeloupe(FBlocks.CANDELOUPE, getLocation("candeloupe"));
        candeloupe(FBlocks.CARVED_CANDELOUPE, getLocation("carved_candeloupe"));
        bulbSack(FBlocks.BULBSACK);
        fuzz(FBlocks.MILDEW_FUZZ);

    }

    private void fuzz(DeferredBlock<? extends Block> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {

            ModelFile model = models()
                    .withExistingParent(getBlockName(block).getPath(), new ResourceLocation("block/coral_fan"))
                    .texture("fan", blockTexture(block.get()))
                    .renderType("cutout");

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }

    private void forgetMeNow() {
        getVariantBuilder(FBlocks.FORGET_ME_NOW.get()).forAllStates(state -> {

            ModelFile model = models()
                    .withExistingParent(getBlockName(FBlocks.FORGET_ME_NOW).getPath(), new ResourceLocation("frostwork:block/small_flower"))
                    .texture("0", getLocation("forget_me_now"))
                    .renderType("cutout");

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }

    private void partialEmissive(DeferredBlock<? extends Block> block, String renderType) {
        var name = block.getId().getPath();

        getVariantBuilder(block.get()).forAllStates(state -> {

            ModelFile model = models()
                    .withExistingParent(getBlockName(block).getPath(), new ResourceLocation("frostwork:block/simple_emissive"))
                    .texture("texture", getLocation(name))
                    .texture("emissive", getLocation(name) + "_emissive")
                    .renderType(ResourceLocation.tryParse(renderType));

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    private void pelt(DeferredBlock<PeltBlock> block) {
        var name = block.getId().getPath();

        getVariantBuilder(block.get()).forAllStates(state -> {

            ModelFile model = models().withExistingParent(getBlockName(block).getPath(), new ResourceLocation("frostwork:block/hunter_pelt"))
                    .texture("0", getLocation(name))
                    .renderType("cutout");

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                    .build();
        });
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

    private void dryGrass() {
        var name = FBlocks.DRY_GRASS.getId().getPath();
        getVariantBuilder(FBlocks.DRY_GRASS.get()).forAllStates(state -> {
            var model = models().cubeBottomTop(name,
                    getLocation(name + "_side"),
                    blockTexture(Blocks.DIRT),
                    getLocation(name + "_top"));

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }
}
