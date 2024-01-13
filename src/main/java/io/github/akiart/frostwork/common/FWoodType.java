//package io.github.akiart.frostwork.common.init;
//
//import it.unimi.dsi.fastutil.objects.ObjectArraySet;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.block.state.properties.WoodType;
//
//import java.util.Set;
//import java.util.stream.Stream;
//
//public class FWoodType {
//    private static final Set<WoodType> VALUES = new ObjectArraySet<>();
//
//    public static final WoodType FROZEN_ELM = create("frozen_elm");
//    public static final WoodType FROZEN_SPRUCE = create("frozen_spruce");
//    public static final WoodType ELM = create("elm");
//    public static final WoodType ASPEN = create("aspen");
//    public static final WoodType TALLOW = create("tallow");
//
//    protected static WoodType create(String name, String soundType) {
//        WoodType type = WoodType.register(new WoodType(name, soundType, ));
//        VALUES.add(type);
//        return type;
//    }
//
//    public static Stream<WoodType> values() {
//        return VALUES.stream();
//    }
//
//    public static String getName(WoodType woodType) {
//        return new ResourceLocation(woodType.name()).getPath();
//    }
//}
