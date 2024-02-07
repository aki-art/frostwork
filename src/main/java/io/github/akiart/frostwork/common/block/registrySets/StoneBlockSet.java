package io.github.akiart.frostwork.common.block.registrySets;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Arrays;
import java.util.List;

public class StoneBlockSet extends AbstractBlockSet {
    public final DeferredBlock<Block> block;
    public final DeferredBlock<StairBlock> stairs;
    public final DeferredBlock<SlabBlock> slab;
    public final DeferredBlock<WallBlock> wall;
    public final DeferredBlock<PressurePlateBlock> pressurePlate;
    public final DeferredBlock<ButtonBlock> button;

    public StoneBlockSet(String name, float hardness, float resistance, MapColor color, boolean redstoneComponents) {
        this(name, redstoneComponents, BlockBehaviour.Properties.of()
                .mapColor(color)
                .requiresCorrectToolForDrops()
                .strength(hardness, resistance));
    }

    public StoneBlockSet(String name, boolean redstoneComponents, BlockBehaviour.Properties properties) {
        super(name, properties);

        block = createBlock(properties);

        stairs = register(name + "_stairs",
                () -> new StairBlock(() -> block.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(block.get())));

        slab = register(name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(block.get())));

        wall = register(name + "_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(block.get())));

        pressurePlate = redstoneComponents ? register(name + "_pressure_plate",
                () -> new PressurePlateBlock(BlockSetType.STONE,BlockBehaviour.Properties.ofFullCopy(block.get())
                        .strength(0.5F))) : null;

        button = redstoneComponents ? register(name + "_button",
                () -> new ButtonBlock(
                        BlockSetType.STONE,
                        1,
                        BlockBehaviour.Properties.of()
                                .noCollission()
                                .strength(0.5F))) : null;
    }

    public DeferredBlock<Block> createBlock(BlockBehaviour.Properties properties) {
        return register(name,
                () -> new Block(properties));
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean hasRedStoneComponents() {
        return button != null && pressurePlate != null;
    }

    public List<DeferredBlock<? extends Block>> getItems() {
        List<DeferredBlock<? extends Block>> result = Arrays.asList(block, stairs, slab, wall);

        if(hasRedStoneComponents()) {
            result.add(pressurePlate);
            result.add(button);
        }

        return result;
    }
}
