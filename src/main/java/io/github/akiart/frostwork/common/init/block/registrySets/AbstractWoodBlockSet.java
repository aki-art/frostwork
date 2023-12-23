package io.github.akiart.frostwork.common.init.block.registrySets;

import io.github.akiart.frostwork.common.init.block.BlockRegistryUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public abstract class AbstractWoodBlockSet extends AbstractBlockSet {

    protected final WoodType woodType;
    protected SoundType plankSoundType;

    // public final DeferredBlock<Block> barrel
    // public final DeferredBlock<Block> bookShelf
    public final DeferredBlock<ButtonBlock> button;
    //public final DeferredBlock<FChestBlock> chest;
    // public final DeferredBlock<Block> craftingTable;
    public final DeferredBlock<DoorBlock> door;
    public final DeferredBlock<TrapDoorBlock> trapDoor;
    public final DeferredBlock<FenceBlock> fence;
    public final DeferredBlock<FenceGateBlock> fenceGate;
    // public final DeferredBlock<Block> ladder;


    public final DeferredBlock<Block> planks;
    public final DeferredBlock<PressurePlateBlock> pressurePlate;
    //public final DeferredBlock<FSignBlock> sign;
    //public final DeferredBlock<FWallSignBlock> wallSign;

    public final DeferredBlock<SlabBlock> slab;
    public final DeferredBlock<StairBlock> stairs;


    protected AbstractWoodBlockSet(String name, MapColor plankColor, MapColor barkColor, WoodType woodType, BlockBehaviour.Properties properties)
    {
        super(name, properties);

        this.woodType = woodType;

        planks = createPlanks(name, plankColor);
        //sapling = createSapling(name, tree);
        stairs = createStairs(name, plankColor);
        slab = createSlab(name, plankColor);
        door = createDoor(name, plankColor);
        trapDoor = createTrapDoor(name, plankColor);
        button = createButton(name);
        pressurePlate = createPressurePlate(name, plankColor);
        fence = createFence(name, plankColor);
        fenceGate = createFenceGate(name, plankColor);
        //sign = createSign(name, woodType);
        //wallSign = createWallSign(name, woodType);
        //chest = createChest(name, woodType);

        plankSoundType = SoundType.WOOD;
    }

    private DeferredBlock<FenceGateBlock> createFenceGate(String name, MapColor plankColor) {
        return register(name + "_fence_gate",
                () -> new FenceGateBlock(woodType, BlockBehaviour.Properties.ofFullCopy(planks.get())
                        .mapColor(plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(plankSoundType)));
    }

    private DeferredBlock<FenceBlock> createFence(String name, MapColor plankColor) {
        return BlockRegistryUtil.register(name + "_fence",
                () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(planks.get())
                        .mapColor(plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(plankSoundType)));
    }

    private DeferredBlock<DoorBlock> createDoor(String name, MapColor plankColor) {
        return register(name + "_door",
                () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_DOOR)
                        .mapColor(plankColor)
                        .strength(3.0F)
                        .sound(SoundType.WOOD)
                        .noOcclusion()));
    }

    private DeferredBlock<TrapDoorBlock> createTrapDoor(String name, MapColor plankColor) {
        return register(name + "_trapdoor",
                () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_TRAPDOOR)
                        .strength(3.0F)
                        .mapColor(plankColor)
                        .sound(SoundType.WOOD)
                        .noOcclusion()
                        .isValidSpawn(BlockRegistryUtil::isValidSpawn)));
    }
    protected DeferredBlock<LeavesBlock> createLeaves(String name, MapColor leavesColor) {
        return register(name + "_leaves",
                () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_LEAVES)
                        .mapColor(leavesColor)
                        .strength(0.2F)
                        .randomTicks()
                        .sound(SoundType.AZALEA_LEAVES)
                        .noOcclusion()
                        .isViewBlocking((state, world, pos) -> false)
                        .isSuffocating((state, world, pos) -> false)));
    }

    private DeferredBlock<ButtonBlock> createButton(String name) {
        return register(name + "_button", () -> new ButtonBlock(BlockSetType.OAK, 1, BlockBehaviour.Properties.of()
                .noCollission()
                .strength(0.5F)));
    }

    private DeferredBlock<PressurePlateBlock> createPressurePlate(String name, MapColor plankColor) {
        return register(name + "_pressure_plate", () -> new PressurePlateBlock(
                BlockSetType.OAK,
                BlockBehaviour.Properties.of()
                        .mapColor(plankColor)
                        .noCollission()
                        .strength(0.5F)));
    }

    private DeferredBlock<StairBlock> createStairs(String name, MapColor mapColor) {
        return register(name + "_stairs",
                () -> new StairBlock(() -> planks.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(planks.get())
                        .mapColor(mapColor)));
    }

    private DeferredBlock<SlabBlock> createSlab(String name, MapColor mapColor) {
        return register(name + "_slab",
                () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(planks.get())
                        .mapColor(mapColor)));
    }


    private DeferredBlock<Block> createPlanks(String name, MapColor plankColor) {
        return register(name + "_planks",
                () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)
                        .mapColor(plankColor)
                        .strength(2.0F, 3.0F)
                        .sound(plankSoundType)));
    }

    protected DeferredBlock<RotatedPillarBlock> log(String name, MapColor plankColor, MapColor barkColor, String suffix) {
        return register(name + suffix,
                () -> new RotatedPillarBlock(properties
                        .mapColor((state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? plankColor : barkColor)
                        .sound(plankSoundType)
                        .strength(2.0f)));
    }

    protected DeferredBlock<RotatedPillarBlock> log(String name, MapColor color, String suffix) {
        return register(name + suffix,
                () -> new RotatedPillarBlock(properties
                        .mapColor(color)
                        .sound(plankSoundType)
                        .strength(2.0f)));
    }
}
