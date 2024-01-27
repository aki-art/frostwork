package io.github.akiart.frostwork.common.fluid;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.fluid.fluidTypes.AcidFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, Frostwork.MOD_ID);

    public static DeferredHolder<Fluid, FlowingFluid> ACID_SOURCE = FLUIDS.register("acid_source", () ->
            new BaseFlowingFluid.Source(AcidFluid.getProperties()));

    public static DeferredHolder<Fluid, FlowingFluid> ACID_FLOWING = FLUIDS.register("acid_flowing", () ->
            new BaseFlowingFluid.Flowing(AcidFluid.getProperties()));
}
