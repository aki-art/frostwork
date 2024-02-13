package io.github.akiart.frostwork.common.block;

import io.github.akiart.frostwork.common.block.blockTypes.FlippableSaplingBlock;
import io.github.akiart.frostwork.common.block.blockTypes.PeltBlock;
import io.github.akiart.frostwork.common.block.registrySets.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.HashSet;
import java.util.function.Supplier;

public class BlockRegistryUtil {

    public static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> supplier) {
        return FBlocks.BLOCKS.register(name, supplier);
    }

    private static HashSet<StoneBlockSet> stones = new HashSet<>();
    private static HashSet<AbstractWoodBlockSet> woods = new HashSet<>();
    private static HashSet<MushroomBlockSet> mushrooms = new HashSet<>();

    public static DeferredBlock<TallFlowerBlock> basicTallFlower(String name) {
        return register(name, () -> new TallFlowerBlock(BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .instabreak()
                .sound(SoundType.GRASS)
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)));
    }

    public static WoodBlockSet registerTransparentWoodSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType, TreeGrower grower) {
        var set = new WoodBlockSet(name, plankColor, barkColor, leavesColor, woodType, grower);
        set.doorRenderType = "translucent";
        woods.add(set);

        return set;
    }

    public static WoodBlockSet registerVelWoodSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType, TreeGrower grower) {
        var set = new WoodBlockSet(name, plankColor, barkColor, leavesColor, woodType, grower) {
            @Override
            protected DeferredBlock<SaplingBlock> sapling(String name, TreeGrower grower, MapColor color) {
                return register(name + "_sapling", () -> new FlippableSaplingBlock(grower, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_SAPLING)
                        .mapColor(color)));
            }
        };

        woods.add(set);

        return set;
    }

    public static WoodBlockSet registerWoodSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType, TreeGrower grower) {
        var set = new WoodBlockSet(name, plankColor, barkColor, leavesColor, woodType, grower);
        woods.add(set);

        return set;
    }

    public static MushroomBlockSet registerMushroomSet(String name, MapColor plankColor, MapColor barkColor, WoodType woodType, SoundType soundType) {
        var set = new MushroomBlockSet(name, plankColor, barkColor, woodType, soundType);
        mushrooms.add(set);

        return set;
    }


    public static StoneBlockSet registerStones(String name, float hardness, float resistance, MapColor color,
                                               boolean redstone) {
        StoneBlockSet obj = new StoneBlockSet(name, hardness, resistance, color, redstone);
        stones.add(obj);
        return obj;
    }

    public static StoneBlockSet registerStones(String name, boolean redstone, BlockBehaviour.Properties properties) {
        StoneBlockSet obj = new StoneBlockSet(name, redstone, properties);
        stones.add(obj);
        return obj;
    }

    public static StoneBlockSet registerStones(String name, StoneBlockSet set) {
        stones.add(set);
        return set;
    }

    public static HashSet<StoneBlockSet> getStones() {
        return stones;
    }
    public static HashSet<AbstractWoodBlockSet> getWoods()
    {
        return woods;
    }
    public static HashSet<MushroomBlockSet> getMushrooms()
    {
        return mushrooms;
    }

    public static boolean isValidSpawn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return false;
    }

    public static ThinWoodBlockSet registerThinWoodSet(String name, MapColor plankColor, MapColor barkColor, MapColor leavesColor, WoodType woodType, TreeGrower grower) {
        var set = new ThinWoodBlockSet(name, plankColor, barkColor, leavesColor, woodType, grower, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_WOOD));
        woods.add(set);

        return set;
    }

    public static DeferredBlock<PeltBlock> registerPelt(String name, MapColor color) {
        return register(name, () -> new PeltBlock(BlockBehaviour.Properties.of()
                .sound(SoundType.WOOL)
                .ignitedByLava()
                .mapColor(color)
                .strength(0.1F)
        ));
    }
}
