package io.github.akiart.frostwork.mixin;

import io.github.akiart.frostwork.common.worldgen.densityFunctions.ISeededDensityFunction;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(RandomState.class)
public class RandomStateMixin {
    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void init(NoiseGeneratorSettings pSettings, HolderGetter<NormalNoise.NoiseParameters> pNoiseParametersGetter, long pLevelSeed, CallbackInfo ci) {

        DensityFunction.Visitor visitor = new DensityFunction.Visitor() {
            private final Map<DensityFunction, DensityFunction> wrapped = new HashMap<>();

            private DensityFunction wrapNew(DensityFunction densityFunction) {

                if(densityFunction instanceof ISeededDensityFunction seededDensityFunction) {
                    seededDensityFunction.setSeed(pLevelSeed);
                }

                return densityFunction;
            }

            @Override
            public @NotNull DensityFunction apply(@NotNull DensityFunction densityFunction) {
                return this.wrapped.computeIfAbsent(densityFunction, this::wrapNew);
            }
        };

        pSettings.noiseRouter().finalDensity().mapAll(visitor);
    }
}
