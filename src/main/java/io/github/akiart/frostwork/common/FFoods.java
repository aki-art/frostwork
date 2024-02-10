package io.github.akiart.frostwork.common;

import net.minecraft.world.food.FoodProperties;
public class FFoods {
    public static final FoodProperties CANDELOUPE_SLICE = new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.3F)
            .build();

    public static final FoodProperties DAZZLING_CANDELOUPE_SLICE = new FoodProperties.Builder()
            .nutrition(6)
            .saturationMod(1.2F)
            .build();
}
