//package io.github.akiart.frostwork.mixin;
//
//import io.github.akiart.frostwork.Frostwork;
//import io.github.akiart.frostwork.common.worldgen.densityFunctions.CellularPlateausDensityFunction;
//import net.minecraft.world.level.levelgen.DensityFunction;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(targets = "net/minecraft/world/level/levelgen/RandomState$1")
//public class RandomState_NoiseWiringHelper_Mixin {
//
//    @Inject(method = "wrapNew", at = @At("HEAD"), remap = false)
//    private void wrapNew(DensityFunction densityFunction, CallbackInfoReturnable<DensityFunction> cir) {
//        Frostwork.LOGGER.info("Success!!!");
//        if(densityFunction instanceof CellularPlateausDensityFunction)
//        {
//
//        }
//    }
//}
