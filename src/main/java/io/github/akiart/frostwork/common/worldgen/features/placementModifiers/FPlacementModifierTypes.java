package io.github.akiart.frostwork.common.worldgen.features.placementModifiers;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.Frostwork;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FPlacementModifierTypes {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, Frostwork.MOD_ID);

    public static DeferredHolder<PlacementModifierType<?>, PlacementModifierType<CellularBoundaryFilter>> CELLULAR_BOUNDARY = register("cellular_boundary",
            CellularBoundaryFilter.CODEC);

    public static DeferredHolder<PlacementModifierType<?>, PlacementModifierType<RidgedCountPlacement>> RIDGED_NOISE = register("ridged_noise",
            RidgedCountPlacement.CODEC);

    private static <P extends PlacementModifier> DeferredHolder<PlacementModifierType<?>, PlacementModifierType<P>> register(String name, Codec<P> codec) {
        return PLACEMENT_MODIFIERS.register(name, () -> () -> (Codec<P>) codec);
    }
}
