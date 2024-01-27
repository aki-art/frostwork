package io.github.akiart.frostwork.common.fluid;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.fluid.fluidTypes.AcidFluid;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Frostwork.MOD_ID);

    public static DeferredHolder<FluidType, AcidFluid> ACID = FLUID_TYPES.register("acid",
            () -> new AcidFluid(FluidType.Properties.create()
                    .canSwim(true)
                    .canDrown(true)
                    .canExtinguish(true)
                    .canPushEntity(true)
                    .fallDistanceModifier(1f)
                    .supportsBoating(true)
                    .pathType(BlockPathTypes.LAVA)
                    .adjacentPathType(null)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .lightLevel(4)
                    .viscosity(5000)
                    .density(1)));
}
