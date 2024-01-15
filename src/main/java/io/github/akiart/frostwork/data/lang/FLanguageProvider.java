package io.github.akiart.frostwork.data.lang;

import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.registrySets.AbstractWoodBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.MushroomBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.WoodBlockSet;
import io.github.akiart.frostwork.common.effects.FEffects;
import io.github.akiart.frostwork.common.item.FItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class FLanguageProvider extends LanguageProvider {

    public FLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("frostwork.frostwork_tab", "Frostwork");

        enchantments();
        effects();

        stones();
        trees();

        blocks();
        items();

    }

    private void items() {
        add(FItems.TATZELWURM_SCALE.get(), "Tatzelwurm Scale");
        add(FItems.TATZELWURM_ARROW.get(), "Tatzelwurm Arrow");
    }

    private void blocks() {
        add(FBlocks.EDELSTONE_COAL_ORE.get(), "Edelstone Coal Ore");
        add(FBlocks.MALACHITE_ICE_ORE.get(), "Malachite Ore");
        add(FBlocks.WOLF_BLOCK.get(), "Wolf Block");
        add(FBlocks.OVERGROWN_SANGUITE.get(), "Overgrown Sanguite");
        add(FBlocks.OVERGROWTH.get(), "Lichen");
        add(FBlocks.GRIMCAP_GILL.get(), "Grimcap Gill");
        add(FBlocks.LAVENDER.get(), "Lavender");
        add(FBlocks.YARROW.get(), "Yarrow");
        add(FBlocks.FORGET_ME_NOW.get(), "Forget-Me-Now");
        add(FBlocks.FOAM.get(), "Foam");
    }

    private void effects() {
        add(FEffects.FRAIL.get(), "Frail");
        //add(FEffects.FRAIL.get().getDescriptionId(), "Frail");
        add(FEffects.POISON_RESISTANCE.get(), "Poison Resistance");
    }

    private void enchantments() {
        // for Enchantment Descriptions: enchantment.%MOD_ID%.%ENCH_ID%.desc https://github.com/Darkhax-Minecraft/Enchantment-Descriptions
        add("enchantment.frostwork.poison_thorns.desc", "Chance to poison on contact damage.");
        add("enchantment.frostwork.freezing.desc", "Chance to freeze an enemy on hit. Frozen smaller entities are completely stunned, large entities get slowed.");
        add("enchantment.frostwork.freezing_thorns.desc", "Chance to freeze on contact damage. Frozen smaller entities are completely stunned, large entities get slowed.");
        add("enchantment.frostwork.meteor.desc", "Chance to spawn a Meteor homing in on the hit entity, dealing large AoE damage on hit. Higher levels increase chance.");
        add("enchantment.frostwork.homing.desc", "Curves projectiles towards the last hit target. Exclusive to Frostwork ranged weapons.");
        add("enchantment.frostwork.sanguine.desc", "Life-steal.");
        add("enchantment.frostwork.trollskin.desc", "Slowly regenerate health in cold biomes or while standing on ice.");
        add("enchantment.frostwork.poison_protection.desc", "Reduced poison duration, and chance to resist poison entirely.");
        add("enchantment.frostwork.fear.desc", "Chance to afflict a hit target with Fear, which will force them to retreat. (may not work with some unique modded AI-s). Higher level increases chance and duration of effect.");
        add("enchantment.frostwork.polymorph.desc", "Chance to turn the enemy into a random passive creature. The drops and XP will be matching the passive creatures. Level increases chance.");
        add("enchantment.frostwork.gentle_explosions.desc", "Prevents the cannon function of a Frostwork Pickaxe from breaking blocks.");
        add("enchantment.frostwork.bee_thorns.desc", "Releases an angry bee familiar when hit, allied to the wearer. The bee disappears in 15 seconds.");

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
        addStones("Marlstone", FBlocks.MARLSTONE);
        addStones("Aquamire", FBlocks.AQUAMIRE);
        addStones("Polished Aquamire", FBlocks.POLISHED_AQUAMIRE);
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
