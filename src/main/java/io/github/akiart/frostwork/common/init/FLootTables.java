package io.github.akiart.frostwork.common.init;

import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class FLootTables {
    private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<>();

    public static Set<ResourceLocation> get() {
        return LOOT_TABLES;
    }
}
