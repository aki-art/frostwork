package io.github.akiart.frostwork.common.block.registrySets;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Map;

public class MushroomBlockSet extends AbstractWoodBlockSet {
    public final DeferredBlock<RotatedPillarBlock> stem;
    public final DeferredBlock<RotatedPillarBlock> strippedStem;
    public final DeferredBlock<HugeMushroomBlock> cap;
    public final DeferredBlock<MushroomBlock> smallMushroom;

    public MushroomBlockSet(String name, MapColor plankColor, MapColor barkColor, WoodType woodType, SoundType soundType, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        this(name, plankColor, barkColor, woodType, soundType, feature, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_STEM));
    }

    protected MushroomBlockSet(String name, MapColor plankColor, MapColor capColor, WoodType woodType, SoundType soundType, ResourceKey<ConfiguredFeature<?, ?>> feature, BlockBehaviour.Properties properties) {
        super(name, plankColor, woodType, properties);

        cap = createCap(name, capColor);
        stem = log(name, plankColor, "_stem");
        strippedStem = log(name, plankColor, "_stripped_stem");
        smallMushroom = createMushroom(name, feature);
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

    protected DeferredBlock<MushroomBlock> createMushroom(String name, ResourceKey<ConfiguredFeature<?, ?>> growBigMushroom) {
        return register(name + "_mushroom",
        () -> new MushroomBlock(
                growBigMushroom,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_BROWN)
                        .noCollission()
                        .randomTicks()
                        .instabreak()
                        .sound(SoundType.FUNGUS)
                        .lightLevel(state -> 1)
                        .hasPostProcess(((state, level, pos) -> true))
                        .pushReaction(PushReaction.DESTROY)));
    }

}
