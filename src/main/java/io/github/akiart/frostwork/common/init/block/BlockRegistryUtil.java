package io.github.akiart.frostwork.common.init.block;

import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.HashSet;
import java.util.function.Supplier;

public class BlockRegistryUtil {

    public static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> supplier) {
        return FBlocks.BLOCKS.register(name, supplier);
    }

    private static HashSet<StoneBlockSet> stones = new HashSet<StoneBlockSet>();
//    private static HashSet<WoodBlockSet> woods = new HashSet<WoodBlockSet>();

//    public static WoodBlockSet registerWoodSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType) {
//        var set = new WoodBlockSet(name, plankColor, barkColor, leavesColor, woodType);
//        woods.add(set);
//
//        return set;
//    }

    public static StoneBlockSet registerStones(String name, float hardness, float resistance, MapColor color,
                                               boolean redstone) {
        StoneBlockSet obj = new StoneBlockSet(name, hardness, resistance, color, redstone);
        stones.add(obj);
        return obj;
    }

    public static HashSet<StoneBlockSet> getStones() {
        return stones;
    }
//    public static HashSet<WoodBlockSet> getWoods() {
//        return woods;
//    }

    public static boolean isValidSpawn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }
}
