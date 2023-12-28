package io.github.akiart.frostwork.common.worldgen.tree;

import io.github.akiart.frostwork.common.worldgen.FConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class FTreeGrowers {
    public static TreeGrower FROZEN_ELM = new TreeGrower("frozen_elm", Optional.empty(), Optional.of(FConfiguredFeatures.FROZEN_ELM), Optional.empty());;

    public static void initialize() {
    }
}
