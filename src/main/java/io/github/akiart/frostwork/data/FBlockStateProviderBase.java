package io.github.akiart.frostwork.data;

import com.mojang.logging.LogUtils;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

public abstract class FBlockStateProviderBase extends BlockStateProvider {
    public FBlockStateProviderBase(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    private static final Logger LOGGER = LogUtils.getLogger();
    protected ResourceLocation getVanillaLocation(String name) {
        return new ResourceLocation("block/" + name);
    }

    protected ResourceLocation getLocation(String name) {
        return new ResourceLocation(Frostwork.MOD_ID, "block/" + name);
    }

//    private String getName(Block block) {
//        return block.
//    }

//    protected void crossBlock(Block block) {
//        ModelFile model = models()
//                .cross(getName(block), blockTexture(block))
//                .renderType("cutout");
//
//        simpleBlock(block, model);
//    }

//    protected void woods(WoodBlockSet blockSet) {
//
//        ResourceLocation plankTexture = blockTexture(blockSet.planks.get());
//        ResourceLocation logTex = blockTexture(blockSet.log.get());
//        ResourceLocation strippedLogTex = blockTexture(blockSet.strippedLog.get());
//
//        woods(blockSet, plankTexture);
//
//        logBlock(blockSet.log.get());
//        logBlock(blockSet.strippedLog.get());
//        axisBlock(blockSet.strippedWood.get(), strippedLogTex, strippedLogTex);
//        axisBlock(blockSet.wood.get(), logTex, logTex);
//    }

//    protected void woods(AbstractWoodBlockSet set, ResourceLocation plankTexture) {
//        simpleBlock(set.planks.get());
//        simpleBlock(set.leaves.get());
//        slabBlock(set.slab.get(), set.planks.getId(), plankTexture);
//        fenceBlock(set.fence.get(), plankTexture);
//        fenceGateBlock(set.fenceGate.get(), plankTexture);
//        buttonBlock(set.button.get(), plankTexture);
//        pressurePlateBlock(set.pressurePlate.get(), plankTexture);
//        stairsBlock(set.stairs.get(), plankTexture);
//        trapdoorBlock(set.trapDoor.get(), getLocation(set.getName() + "_trapdoor"), true);
//        doorBlock(set.door.get(), getLocation(set.getName() + "_door_bottom"), getLocation(set.getName() + "_door_top"));
//
//    }

    protected void stones(StoneBlockSet blockSet) {
        ResourceLocation texture = blockTexture(blockSet.block.get());

        simpleBlock(blockSet.block.get());
        stairsBlock(blockSet.stairs.get(), texture);
        wallBlock(blockSet.wall.get(), texture);
        slabBlock(blockSet.slab.get(), blockSet.block.getId(), texture);

        if (blockSet.hasRedStoneComponents()) {
            buttonBlock(blockSet.button.get(), texture);
            pressurePlateBlock(blockSet.pressurePlate.get(), texture);
        }
    }
}
