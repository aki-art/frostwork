package io.github.akiart.frostwork.common.block.registrySets;

import io.github.akiart.frostwork.common.block.blockTypes.ThinLogBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;

public class ThinWoodBlockSet extends AbstractWoodBlockSet {

    public final DeferredBlock<LeavesBlock> leaves;
    public final DeferredBlock<SaplingBlock> sapling;
    public final DeferredBlock<ThinLogBlock> log;
    public final DeferredBlock<ThinLogBlock> strippedLog;
    public final DeferredBlock<ThinLogBlock> strippedWood;
    public final DeferredBlock<ThinLogBlock> wood;

    public ThinWoodBlockSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType, TreeGrower grower, BlockBehaviour.Properties properties) {
        super(name, plankColor, woodType, properties);
        
        leaves = createLeaves(name, leavesColor);
        sapling = sapling(name, grower, MapColor.ICE);
        log = thinLog(name, barkColor, "_log");
        wood = thinLog(name, barkColor, "_wood");
        strippedWood = thinLog(name, barkColor, "_stripped_wood");
        strippedLog = thinLog(name, plankColor, barkColor, "_stripped_log");
    }

    @Override
    public void setStripMaps(Map<Block, Block> stripMap) {

    }
}
