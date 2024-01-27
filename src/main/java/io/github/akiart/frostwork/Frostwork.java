package io.github.akiart.frostwork;

import io.github.akiart.frostwork.client.particles.FParticles;
import io.github.akiart.frostwork.common.EntityEvents;
import io.github.akiart.frostwork.common.FCreativeModeTabs;
import io.github.akiart.frostwork.common.FEntityTypes;
import io.github.akiart.frostwork.common.PlayerEvents;
import io.github.akiart.frostwork.common.block.BlockRegistryUtil;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.effects.FEffects;
import io.github.akiart.frostwork.common.fluid.FFluidTypes;
import io.github.akiart.frostwork.common.fluid.FFluids;
import io.github.akiart.frostwork.common.item.FItems;
import io.github.akiart.frostwork.common.potion.FPotions;
import io.github.akiart.frostwork.common.worldgen.*;
import io.github.akiart.frostwork.common.worldgen.features.FFeatures;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod(Frostwork.MOD_ID)
public class Frostwork {
    public static final String MOD_ID = "frostwork";
    public static final Logger LOGGER = LogManager.getLogger();

    public Frostwork(IEventBus modEventBus) {
        initRegistry(modEventBus);

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::worldgenRegistryInit);

        NeoForge.EVENT_BUS.register(EntityEvents.class);
        NeoForge.EVENT_BUS.register(PlayerEvents.class);
    }

    private static void initRegistry(IEventBus modEventBus) {
        FBlocks.BLOCKS.register(modEventBus);
        FItems.ITEMS.register(modEventBus);
        FEffects.EFFECTS.register(modEventBus);
        FPotions.POTIONS.register(modEventBus);
        FEntityTypes.ENTITY_TYPES.register(modEventBus);
        FFluids.FLUIDS.register(modEventBus);
        FFluidTypes.FLUID_TYPES.register(modEventBus);
        FCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        FParticles.PARTICLES.register(modEventBus);
        FFeatures.FEATURES.register(modEventBus);
    }
    public void worldgenRegistryInit(RegisterEvent evt) {
        var key = evt.getRegistryKey();

        if (key.equals(Registries.BIOME_SOURCE))
            Registry.register(BuiltInRegistries.BIOME_SOURCE, new ResourceLocation(MOD_ID, "fantasia"), FantasiaBiomeSource.DATA_CODEC);
        else if(key.equals(Registries.CHUNK_GENERATOR)) {
            Registry.register(BuiltInRegistries.CHUNK_GENERATOR, new ResourceLocation(MOD_ID, "layered_generator"), LayeredNoiseChunkGenerator.CODEC);
        }
        else if(key.equals(Registries.MATERIAL_CONDITION))
            Registry.register(BuiltInRegistries.MATERIAL_CONDITION, new ResourceLocation(MOD_ID, "cellular_sponge"), FSurfaceRules.CellularBoundaryConditionSource.CODEC.codec());
        else if(key.equals(Registries.DENSITY_FUNCTION_TYPE)) {
            Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, new ResourceLocation(MOD_ID, "y_clamped_offset_curve"), FDensityFunctions.YClampedOffsetCurve.CODEC.codec());
            Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, new ResourceLocation(MOD_ID, "warped_simplex"), FDensityFunctions.WarpedSimplexDensityFunction.CODEC.codec());
            Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, new ResourceLocation(MOD_ID, "fantasia_base_surface"), FDensityFunctions.FantasiaBaseSurfaceDensityFunction.CODEC.codec());
            Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, new ResourceLocation(MOD_ID, "fantasia_surface_flat"), FDensityFunctions.FlatSurfaceDensityFunction.CODEC.codec());
            Registry.register(BuiltInRegistries.DENSITY_FUNCTION_TYPE, new ResourceLocation(MOD_ID, "exp_floor"), FDensityFunctions.ExponentialDensityFunction.CODEC.codec());
        }
    }
    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            Map<Block, Block> stripMap = new HashMap<>(AxeItem.STRIPPABLES);
            BlockRegistryUtil.getWoods().forEach(t -> t.setStripMaps(stripMap));
            BlockRegistryUtil.getMushrooms().forEach(t -> t.setStripMaps(stripMap));
            AxeItem.STRIPPABLES = stripMap;
            PotionBrewing.addMix(Potions.AWKWARD, FItems.TATZELWURM_SCALE.get(), FPotions.POISON_RESISTANCE.get());
            //BrewingRecipeRegistry.addRecipe(Ingredient.of(Potions.AWKWARD))

            //FWoodType.values().forEach(WoodType::register);
        });
    }

}
