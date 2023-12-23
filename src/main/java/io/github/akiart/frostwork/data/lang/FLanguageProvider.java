package io.github.akiart.frostwork.data.lang;

import io.github.akiart.frostwork.common.init.FBlocks;
import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
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
    }

    private void stones() {
        addStones("Obsidian Bricks", FBlocks.OBSIDIAN_BRICKS);
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
