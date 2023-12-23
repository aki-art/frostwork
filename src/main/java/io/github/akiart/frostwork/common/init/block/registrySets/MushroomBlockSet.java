package io.github.akiart.frostwork.common.init.block.registrySets;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class MushroomBlockSet extends AbstractWoodBlockSet {
    public final DeferredBlock<RotatedPillarBlock> stem;
    public final DeferredBlock<RotatedPillarBlock> strippedStem;
    public final DeferredBlock<Block> cap;

    public MushroomBlockSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType) {
        this(name, plankColor, barkColor, leavesColor, woodType, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FUNGUS));
    }

    protected MushroomBlockSet(String name, MapColor plankColor, MapColor capColor, MapColor leavesColor, WoodType woodType, BlockBehaviour.Properties properties) {
        super(name, plankColor, capColor, woodType, properties);

        cap = createCap(name, capColor);
        stem = log(name, plankColor, "_stem");
        strippedStem = log(name, plankColor, "_stripped_stem");
    }

    private DeferredBlock<Block> createCap(String name, MapColor capColor) {
        return register(name + "_cap",
                () -> new Block(properties
                        .mapColor(capColor)
                        .strength(2.0F, 3.0F)
                        .sound(plankSoundType)));
    }

}
