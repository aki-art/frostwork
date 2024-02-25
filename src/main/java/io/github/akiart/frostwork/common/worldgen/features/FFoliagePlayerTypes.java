package io.github.akiart.frostwork.common.worldgen.features;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.features.foliagePlacers.NbtFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FFoliagePlayerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE,
            Frostwork.MOD_ID);
    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<NbtFoliagePlacer>> NBT_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("nbt", () -> new FoliagePlacerType<>(NbtFoliagePlacer.CODEC));
}
