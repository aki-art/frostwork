package io.github.akiart.frostwork.data.lang;

import io.github.akiart.frostwork.common.init.FBlocks;
import io.github.akiart.frostwork.common.init.block.registrySets.AbstractWoodBlockSet;
import io.github.akiart.frostwork.common.init.block.registrySets.MushroomBlockSet;
import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.init.block.registrySets.WoodBlockSet;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class FLanguageProvider extends LanguageProvider {

    public FLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("frostwork_tab", "Frostwork Blocks");

        stones();
        trees();

        add(FBlocks.EDELSTONE_COAL_ORE.get(), "Edelstone Coal Ore");
        add(FBlocks.MALACHITE_ICE_ORE.get(), "Malachite Ore");
        add(FBlocks.WOLF_BLOCK.get(), "Wolf Block");
        add(FBlocks.OVERGROWN_SANGUITE.get(), "Overgrown Sanguite");
        add(FBlocks.OVERGROWTH.get(), "Lichen");
        add(FBlocks.GRIMCAP_GILL.get(), "Grimcap Gill");
        add(FBlocks.LAVENDER.get(), "Lavender");
        add(FBlocks.YARROW.get(), "Yarrow");
    }

    private void trees() {
        addTree("Frozen Elm", FBlocks.FROZEN_ELM);
        addTree("Elm", FBlocks.ELM);
        addTree("Grimcap", FBlocks.GRIMCAP);
    }

    private void stones() {
        addStones("Obsidian Bricks", FBlocks.OBSIDIAN_BRICKS);
        addStones("Edelstone", FBlocks.EDELSTONE);
        addStones("Edelstone Brick", FBlocks.EDELSTONE_BRICKS);
        addStones("Sanguite", FBlocks.SANGUITE);
        addStones("Polished Sanguite", FBlocks.POLISHED_SANGUITE);
    }

    private void addTree(String name, AbstractWoodBlockSet woodSet) {
        add(woodSet.planks.get(), name + " Planks");
        add(woodSet.stairs.get(), name + " Plank Stairs");
        add(woodSet.slab.get(), name + " Plank Slab");
        add(woodSet.door.get(), name + " Door");
        add(woodSet.fence.get(), name + " Fence");
        add(woodSet.fenceGate.get(), name + " Fence Gate");
        add(woodSet.button.get(), name + " Button");
        add(woodSet.pressurePlate.get(), name + " Pressure Plate");
        add(woodSet.trapDoor.get(), name + " Trapdoor");

        if(woodSet instanceof WoodBlockSet wood) {
            add(wood.leaves.get(), name + " Leaves");
            add(wood.log.get(), name + " Log");
            add(wood.strippedWood.get(), "Stripped " + name + " Wood");
            add(wood.strippedLog.get(), "Stripped " + name + " Log");
            add(wood.wood.get(), name + " Wood");
            add(wood.sapling.get(), name + " Sapling");
        }
        else if(woodSet instanceof MushroomBlockSet mushroom) {
            add(mushroom.cap.get(), name + " Cap");
            add(mushroom.stem.get(), name + " Stem");
            add(mushroom.strippedStem.get(), "Stripped " + name + " Stem");
        }
    }

    private void addStones(String name, StoneBlockSet stoneSet) {
        add(stoneSet.block.get(), name);
        add(stoneSet.stairs.get(), name + " Stairs");
        add(stoneSet.slab.get(), name + " Slab");
        add(stoneSet.wall.get(), name + " Wall");

        if(stoneSet.hasRedStoneComponents()) {
            add(stoneSet.button.get(), name + " Button");
            add(stoneSet.pressurePlate.get(), name + " Pressure Plate");
        }
    }
}
