package io.github.akiart.frostwork.common.init.block.registrySets;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class MushroomBlockSet extends AbstractWoodBlockSet {
    public final DeferredBlock<RotatedPillarBlock> stem;
    public final DeferredBlock<RotatedPillarBlock> strippedStem;
    public final DeferredBlock<Block> cap;

    public MushroomBlockSet(String name, MapColor plankColor, MapColor barkColor, WoodType woodType, SoundType soundType) {
        this(name, plankColor, barkColor, woodType, soundType, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_STEM));
    }

    protected MushroomBlockSet(String name, MapColor plankColor, MapColor capColor, WoodType woodType, SoundType soundType, BlockBehaviour.Properties properties) {
        super(name, plankColor, woodType, properties);

        cap = createCap(name, capColor);
        stem = log(name, plankColor, "_stem");
        strippedStem = log(name, plankColor, "_stripped_stem");
    }

    private DeferredBlock<Block> createCap(String name, MapColor capColor) {
        return register(name + "_cap",
                () -> new Block(properties
                        .mapColor(capColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.FUNGUS)));
    }

}
