package io.github.akiart.frostwork.common.block;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.blockTypes.*;
import io.github.akiart.frostwork.common.block.registrySets.MushroomBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.WoodBlockSet;
import io.github.akiart.frostwork.common.fluid.FFluids;
import io.github.akiart.frostwork.common.worldgen.features.tree.FTreeGrowers;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Frostwork.MOD_ID);

    public static class TUNING {
        public static final float COBBLE_HARDNESS = 2f;
        public static final float COBBLE_RESISTANCE = 6f;
    }

    // Trees
    public static final WoodBlockSet FROZEN_ELM = BlockRegistryUtil.registerTransparentWoodSet("frozen_elm", MapColor.COLOR_LIGHT_BLUE, MapColor.LAPIS, MapColor.ICE, WoodType.OAK, FTreeGrowers.FROZEN_ELM);
    public static final WoodBlockSet ELM = BlockRegistryUtil.registerTransparentWoodSet("elm", MapColor.WOOD, MapColor.TERRACOTTA_BROWN, MapColor.COLOR_GREEN, WoodType.OAK, FTreeGrowers.ELM);
    //public static final ThinWoodBlockSet ASPEN = BlockRegistryUtil.registerThinWoodSet("aspen", MapColor.SAND, MapColor.SNOW, MapColor.COLOR_YELLOW, WoodType.BIRCH, FTreeGrowers.ELM);

    // Mushrooms
    public static final MushroomBlockSet GRIMCAP = BlockRegistryUtil.registerMushroomSet("grimcap", MapColor.SAND, MapColor.NETHER, WoodType.OAK, SoundType.FUNGUS);

    public static final DeferredBlock<Block> GRIMCAP_GILL = BlockRegistryUtil.register("grimcap_gill",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(GRIMCAP.cap.get())
                    .noCollission()));

    // Stones
    public static final StoneBlockSet OBSIDIAN_BRICKS = BlockRegistryUtil.registerStones("obsidian_bricks", 5f, 20f, MapColor.COLOR_BLACK, true);
    public static final StoneBlockSet EDELSTONE = BlockRegistryUtil.registerStones("edelstone", TUNING.COBBLE_HARDNESS, TUNING.COBBLE_RESISTANCE, MapColor.CLAY, true);
    public static final StoneBlockSet CRACKED_EDELSTONE_BRICKS = BlockRegistryUtil.registerStones("cracked_edelstone_bricks", TUNING.COBBLE_HARDNESS, TUNING.COBBLE_RESISTANCE, MapColor.CLAY, false);
    public static final StoneBlockSet POLISHED_EDELSTONE = BlockRegistryUtil.registerStones("polished_edelstone", TUNING.COBBLE_HARDNESS, TUNING.COBBLE_RESISTANCE, MapColor.CLAY, false);
    public static final StoneBlockSet EDELSTONE_BRICKS = BlockRegistryUtil.registerStones("edelstone_bricks", TUNING.COBBLE_HARDNESS, TUNING.COBBLE_RESISTANCE, MapColor.CLAY, false);

    public static final StoneBlockSet MARLSTONE = BlockRegistryUtil.registerStones("marlstone", 5f, 20f, MapColor.CLAY, false);
    public static final StoneBlockSet MARLSTONE_BRICKS = BlockRegistryUtil.registerStones("marlstone_bricks", 5f, 20f, MapColor.CLAY, false);

    public static final StoneBlockSet AQUAMIRE = BlockRegistryUtil.registerStones("aquamire", 5f, 20f, MapColor.COLOR_LIGHT_BLUE, false);
    public static final StoneBlockSet VERDANT_ROCK = BlockRegistryUtil.registerStones("verdant_rock", 5f, 20f, MapColor.COLOR_GREEN, false);
    public static final StoneBlockSet PITH = BlockRegistryUtil.registerStones("pith", 5f, 20f, MapColor.COLOR_YELLOW, false);
    public static final StoneBlockSet POLISHED_AQUAMIRE = BlockRegistryUtil.registerStones("polished_aquamire", 5f, 20f, MapColor.COLOR_LIGHT_BLUE, false);

    public static final StoneBlockSet SANGUITE = BlockRegistryUtil.registerStones("sanguite", TUNING.COBBLE_HARDNESS, TUNING.COBBLE_RESISTANCE, MapColor.NETHER, false);
    public static final StoneBlockSet POLISHED_SANGUITE = BlockRegistryUtil.registerStones("polished_sanguite", TUNING.COBBLE_HARDNESS, TUNING.COBBLE_RESISTANCE, MapColor.NETHER, false);
    public static final StoneBlockSet POLISHED_MALACHITE = BlockRegistryUtil.registerStones("polished_malachite", TUNING.COBBLE_HARDNESS, TUNING.COBBLE_RESISTANCE, MapColor.COLOR_GREEN, false);

    // Ores
    public static final DeferredBlock<Block> EDELSTONE_COAL_ORE = BlockRegistryUtil.register("edelstone_coal_ore", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_ORE)
            .sound(SoundType.DEEPSLATE)
            .mapColor(MapColor.COLOR_BLACK)));

    public static final DeferredBlock<FTransparentBlock> MALACHITE_ICE_ORE = BlockRegistryUtil.register("malachite_ice_ore", () -> new FTransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ICE)
            .sound(SoundType.GLASS)
            .noOcclusion()
            .hasPostProcess((state, getter, pos) -> true)
            .emissiveRendering((state, getter, pos) -> false)

            .lightLevel(state -> 0)
            .mapColor(MapColor.ICE)));

    public static final DeferredBlock<Block> MALACHITE_BLOCK = BlockRegistryUtil.register("malachite_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK)
                    .lightLevel(state -> 3)
                    .mapColor(MapColor.COLOR_GREEN)));

    // Plants
    public static DeferredBlock<TallFlowerBlock> LAVENDER = BlockRegistryUtil.basicTallFlower("lavender");
    public static DeferredBlock<TallFlowerBlock> YARROW = BlockRegistryUtil.basicTallFlower("yarrow");
    public static final DeferredBlock<ForgetMeNowBlock> FORGET_ME_NOW = BlockRegistryUtil.register("forget_me_now",
            () -> new ForgetMeNowBlock(MobEffects.WEAKNESS, 40, BlockBehaviour.Properties.ofFullCopy(Blocks.WITHER_ROSE)));

    public static final DeferredBlock<FlowerBlock> BEARBERRY = BlockRegistryUtil.register("bearberry",
            () -> new FlowerBlock(() -> MobEffects.ABSORPTION, 4, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.FLOWERING_AZALEA)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<LanternBlock> CANDELOUPE = BlockRegistryUtil.register("candeloupe",
            () -> new LanternBlock(BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .mapColor(MapColor.GLOW_LICHEN)
                    .lightLevel(state -> 8)
                    .forceSolidOn()
                    .noOcclusion()
                    .strength(1.0F)
                    .sound(SoundType.FUNGUS)
                    .pushReaction(PushReaction.DESTROY)
                    .emissiveRendering((state, level, pos) -> true)));

    // Misc
    public static final DeferredBlock<SnowyDirtBlock> FROZEN_DIRT = BlockRegistryUtil.register("frozen_dirt",
            () -> new SnowyDirtBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)));

    public static final DeferredBlock<PeltBlock> HUNTER_PELT_BROWN = BlockRegistryUtil.registerPelt("hunter_pelt_brown", MapColor.WOOD);
    public static final DeferredBlock<PeltBlock> HUNTER_PELT_CREAM = BlockRegistryUtil.registerPelt("hunter_pelt_cream", MapColor.TERRACOTTA_WHITE);
    public static final DeferredBlock<Block> OVERGROWN_SANGUITE = BlockRegistryUtil.register("overgrown_sanguite",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(SANGUITE.block.get())));

    public static final DeferredBlock<Block> WOLF_BLOCK = BlockRegistryUtil.register("wolf_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)));
    public static final DeferredBlock<Block> DRY_GRASS = BlockRegistryUtil.register("dry_grass", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)));
    public static final DeferredBlock<CarpetBlock> OVERGROWTH = BlockRegistryUtil.register("overgrowth", () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));
    public static final DeferredBlock<FoamBlock> FOAM = BlockRegistryUtil.register("foam",
            () -> new FoamBlock(BlockBehaviour.Properties
                    .ofFullCopy(Blocks.HONEY_BLOCK)
                    .noCollission()
                    .noOcclusion()));

    public static final DeferredBlock<AcidLiquidBlock> ACID = BlockRegistryUtil.register("acid",
            () -> new AcidLiquidBlock(FFluids.ACID_SOURCE, BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
                    .lightLevel(state -> 4)
                    .mapColor(MapColor.COLOR_LIGHT_GREEN)));
}
