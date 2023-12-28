package io.github.akiart.frostwork.common.init.block.registrySets;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;

public class WoodBlockSet extends AbstractWoodBlockSet {
    public final DeferredBlock<RotatedPillarBlock> log;
    public final DeferredBlock<RotatedPillarBlock> strippedLog;
    public final DeferredBlock<RotatedPillarBlock> strippedWood;
    public final DeferredBlock<RotatedPillarBlock> wood;
    public final DeferredBlock<LeavesBlock> leaves;
    public final DeferredBlock<SaplingBlock> sapling;

    public WoodBlockSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType, TreeGrower grower) {
        this(name, plankColor, barkColor, leavesColor, woodType, grower, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_WOOD));
    }

    public WoodBlockSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType, TreeGrower grower, BlockBehaviour.Properties properties) {
        super(name, plankColor, woodType, properties);

        log = log(name, barkColor, "_log");
        wood = log(name, barkColor, "_wood");
        strippedWood = log(name, barkColor, "_stripped_wood");
        strippedLog = log(name, plankColor, barkColor, "_stripped_log");
        leaves = createLeaves(name, leavesColor);
        sapling = sapling(name, grower, MapColor.ICE);
    }

    @Override
    public void setStripMaps(Map<Block, Block> stripMap) {
        stripMap.put(log.get(), strippedLog.get());
        stripMap.put(wood.get(), strippedWood.get());
    }
}
