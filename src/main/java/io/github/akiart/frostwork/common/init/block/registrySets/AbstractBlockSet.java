package io.github.akiart.frostwork.common.init.block.registrySets;

import io.github.akiart.frostwork.common.init.block.FBlocks;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

public abstract class AbstractBlockSet {
    public static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> supplier) {
        return FBlocks.BLOCKS.register(name, supplier);
    }

    public abstract String getName();
}
