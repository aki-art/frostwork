package io.github.akiart.frostwork.common.worldgen.features;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class FTreeGrowers {
    public static TreeGrower FROZEN_ELM = new TreeGrower("frozen_elm", Optional.empty(), Optional.of(FConfiguredFeatures.Vegetation.Trees.FROZEN_ELM), Optional.empty());;
    public static TreeGrower ELM = new TreeGrower("elm", Optional.empty(), Optional.of(FConfiguredFeatures.Vegetation.Trees.ELM), Optional.empty());;
    public static TreeGrower VELWOOD = new TreeGrower("velwood",
            Optional.of(FConfiguredFeatures.Vegetation.Trees.VELWOOD_DOUBLE),
            Optional.of(FConfiguredFeatures.Vegetation.Trees.VELWOOD_SINGLE_SHORT),
            Optional.empty());
}