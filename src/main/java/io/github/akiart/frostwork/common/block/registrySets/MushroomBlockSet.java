package io.github.akiart.frostwork.common.block.registrySets;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;

public class MushroomBlockSet extends AbstractWoodBlockSet {
    public final DeferredBlock<RotatedPillarBlock> stem;
    public final DeferredBlock<RotatedPillarBlock> strippedStem;
    public final DeferredBlock<HugeMushroomBlock> cap;

    public MushroomBlockSet(String name, MapColor plankColor, MapColor barkColor, WoodType woodType, SoundType soundType) {
        this(name, plankColor, barkColor, woodType, soundType, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_STEM));
    }

    protected MushroomBlockSet(String name, MapColor plankColor, MapColor capColor, WoodType woodType, SoundType soundType, BlockBehaviour.Properties properties) {
        super(name, plankColor, woodType, properties);

        cap = createCap(name, capColor);
        stem = log(name, plankColor, "_stem");
        strippedStem = log(name, plankColor, "_stripped_stem");
    }

    @Override
    public void setStripMaps(Map<Block, Block> stripMap) {
        stripMap.put(stem.get(), strippedStem.get());
    }

    private DeferredBlock<HugeMushroomBlock> createCap(String name, MapColor capColor) {
        return register(name + "_cap",
                () -> new HugeMushroomBlock(properties
                        .mapColor(capColor)
                        .strength(2.0F, 3.0F)
                        .sound(SoundType.FUNGUS)));
    }

}
