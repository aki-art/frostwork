package io.github.akiart.frostwork.common.block.registrySets;

import io.github.akiart.frostwork.common.block.FBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

public abstract class AbstractBlockSet {

    public final String name;
    protected final BlockBehaviour.Properties properties;

    public String doorRenderType = "cutout";

    public String getName() {
        return name;
    }

    public AbstractBlockSet(String name, BlockBehaviour.Properties properties) {
        this.name = name;
        this.properties = properties;
    }

    public static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> supplier) {
        return FBlocks.BLOCKS.register(name, supplier);
    }
}
