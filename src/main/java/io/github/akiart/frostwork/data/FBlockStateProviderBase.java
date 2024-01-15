package io.github.akiart.frostwork.data;

import com.mojang.logging.LogUtils;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.blockTypes.FoamBlock;
import io.github.akiart.frostwork.common.block.registrySets.AbstractWoodBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.MushroomBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.WoodBlockSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.slf4j.Logger;

public abstract class FBlockStateProviderBase extends BlockStateProvider {
    public FBlockStateProviderBase(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    private static final Logger LOGGER = LogUtils.getLogger();
    protected ResourceLocation getVanillaLocation(String name) {
        return new ResourceLocation("block/" + name);
    }

    protected void foam(DeferredBlock<? extends FoamBlock> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {

            var stage = state.getValue(FoamBlock.FOAM_AMOUNT);
            String name = getBlockName(block).getPath() + "_" + stage;

            ModelFile model = models()
                    .singleTexture(name, getVanillaLocation("lily_pad"), getLocation(name))
                    .renderType("translucent");

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }

    protected ResourceLocation getLocation(String name) {
        return new ResourceLocation(Frostwork.MOD_ID, "block/" + name);
    }

//    private String getName(Block block) {
//        return block.
//    }

    protected void crossBlock(DeferredBlock<? extends Block> block) {
        ModelFile model = models()
                .cross(getBlockName(block).getPath(), blockTexture(block.get()))
                .renderType("cutout");

        simpleBlock(block.get(), model);
    }

    protected void mushroom(MushroomBlockSet blockSet) {

        ResourceLocation plankTexture = blockTexture(blockSet.planks.get());

        woods(blockSet, plankTexture);
        logBlock(blockSet.stem.get());
        simpleBlock(blockSet.cap.get());
        logBlock(blockSet.strippedStem.get());
    }

    protected void wood(AbstractWoodBlockSet blockSet) {

        ResourceLocation plankTexture = blockTexture(blockSet.planks.get());
        woods(blockSet, plankTexture);

        if(blockSet instanceof WoodBlockSet wood) {
            ResourceLocation logTex = blockTexture(wood.log.get());
            ResourceLocation strippedLogTex = blockTexture(wood.strippedLog.get());

            simpleBlock(wood.leaves.get());

            logBlock(wood.log.get());
            logBlock(wood.strippedLog.get());
            axisBlock(wood.strippedWood.get(), strippedLogTex, strippedLogTex);
            axisBlock(wood.wood.get(), logTex, logTex);

            crossBlock(wood.sapling);
        }
    }

    protected void woods(AbstractWoodBlockSet set, ResourceLocation plankTexture) {
        simpleBlock(set.planks.get());
        slabBlock(set.slab.get(), set.planks.getId(), plankTexture);
        fenceBlock(set.fence.get(), plankTexture);
        fenceGateBlock(set.fenceGate.get(), plankTexture);
        buttonBlock(set.button.get(), plankTexture);
        pressurePlateBlock(set.pressurePlate.get(), plankTexture);
        stairsBlock(set.stairs.get(), plankTexture);
        trapdoorBlockWithRenderType(
                set.trapDoor.get(),
                getLocation(set.getName() + "_trapdoor"),
                true,
                set.doorRenderType);

        doorBlockWithRenderType(
                set.door.get(),
                getLocation(set.getName() + "_door_bottom"),
                getLocation(set.getName() + "_door_top"),
                set.doorRenderType);
    }

    protected ResourceLocation getBlockName(DeferredBlock<?> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get());
    }

    protected void tallPlant(DeferredBlock<? extends Block> block) {
        String name = getBlockName(block).getPath();

        getVariantBuilder(block.get()).forAllStates(state -> {
            DoubleBlockHalf half = state.getValue(DoublePlantBlock.HALF);
            String partName = name + "_" + half.getSerializedName();

            ModelFile model = models()
                    .cross(partName, getLocation(partName))
                    .renderType("cutout");

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    protected void snowyBlock(SnowyDirtBlock block) {
        getVariantBuilder(block).forAllStates(state -> {

            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);

            ResourceLocation all = blockTexture(block);
            ModelFile model;
            var name = BuiltInRegistries.BLOCK.getKey(block).getPath();

            if (snowy) {
                ResourceLocation side = getLocation("snowy_" + name);
                ResourceLocation top = new ResourceLocation("block/snow");
                model = models().cubeBottomTop(name, side, all, top);
            } else {
                model = models().cubeAll("snowy_" + name, all);
            }

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    protected void stones(StoneBlockSet blockSet) {
        ResourceLocation texture = blockTexture(blockSet.block.get());

        if(blockSet.equals(FBlocks.POLISHED_AQUAMIRE)) {
            Block block = blockSet.block.get();
            getVariantBuilder(block).forAllStates(state -> {

                ResourceLocation all = blockTexture(block);
                ModelFile model;
                var name = BuiltInRegistries.BLOCK.getKey(block).getPath();

                ResourceLocation side = getLocation(name +"_side");

                model = models().cubeColumn(name, side, all);

                return ConfiguredModel.builder().modelFile(model).build();
            });
        }
        else {
            simpleBlock(blockSet.block.get());
        }
        stairsBlock(blockSet.stairs.get(), texture);
        wallBlock(blockSet.wall.get(), texture);
        slabBlock(blockSet.slab.get(), blockSet.block.getId(), texture);

        if (blockSet.hasRedStoneComponents()) {
            buttonBlock(blockSet.button.get(), texture);
            pressurePlateBlock(blockSet.pressurePlate.get(), texture);
        }
    }
}
