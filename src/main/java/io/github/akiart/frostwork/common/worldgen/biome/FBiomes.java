package io.github.akiart.frostwork.common.worldgen.biome;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.worldgen.biome.biomeConfigs.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class FBiomes {
    public static class Surface {
        public static final ResourceKey<Biome> ALPINE_TUNDRA = key("alpine_tundra");

    }

    public static class Cave {
        public static final ResourceKey<Biome> FROZEN_CAVE = key("frozen_cave");
        public static final ResourceKey<Biome> VERDANT_GLADE = key("verdant_glade");
        public static final ResourceKey<Biome> HIVE = key("hive");
        public static final ResourceKey<Biome> GRIMCAP_GROVE = key("grimcap_grove");
    }

    public static class Debug {
        public static final ResourceKey<Biome> DEBUG_RED = key("debug_red");
        public static final ResourceKey<Biome> DEBUG_BLUE = key("debug_blue");
    }

    public static class Sky {
        public static final ResourceKey<Biome> FLOATING_MOUNTAINS = key("floating_mountains");
    }

    public static class Special {

    }

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(Surface.ALPINE_TUNDRA, new AlpineTundraConfig(context).create());

        context.register(Cave.FROZEN_CAVE, new FrozenCaveConfig(context).create());
        context.register(Cave.VERDANT_GLADE, new VerdantGladeConfig(context).create());
        context.register(Cave.GRIMCAP_GROVE, new GrimcapGroveConfig(context).create());
        context.register(Cave.HIVE, new HiveConfig(context).create());

        context.register(Sky.FLOATING_MOUNTAINS, new DebugBiomeConfig(context, 0x10278388).create());

        context.register(Debug.DEBUG_RED, new DebugBiomeConfig(context, 0xFF0000).create());
        context.register(Debug.DEBUG_BLUE, new DebugBiomeConfig(context, 0x000000FF).create());
    }

    private static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Frostwork.MOD_ID, name));
    }
}
