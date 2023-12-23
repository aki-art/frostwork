package io.github.akiart.frostwork.common.init.block.registrySets;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class WoodBlockSet extends AbstractWoodBlockSet {
    public final DeferredBlock<RotatedPillarBlock> log;
    public final DeferredBlock<RotatedPillarBlock> strippedLog;
    public final DeferredBlock<RotatedPillarBlock> strippedWood;
    public final DeferredBlock<RotatedPillarBlock> wood;
    public final DeferredBlock<LeavesBlock> leaves;
    //public final DeferredBlock<FSaplingBlock> sapling;

    public WoodBlockSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType) {
        this(name, plankColor, barkColor, leavesColor, woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_WOOD));
    }

    public WoodBlockSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType, BlockBehaviour.Properties properties) {
        super(name, plankColor, barkColor, woodType, properties);

        log = log(name, barkColor, "_log");
        wood = log(name, barkColor, "_wood");
        strippedWood = log(name, barkColor, "_stripped_wood");
        strippedLog = log(name, plankColor, barkColor, "_stripped_log");
        leaves = createLeaves(name, leavesColor);
    }
}
