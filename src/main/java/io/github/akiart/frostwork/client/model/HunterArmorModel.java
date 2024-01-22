package io.github.akiart.frostwork.client.model;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.item.itemTypes.HunterArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HunterArmorModel extends GeoModel<HunterArmorItem> {
    @Override
    public ResourceLocation getModelResource(HunterArmorItem animatable) {
        return new ResourceLocation(Frostwork.MOD_ID, "geo/hunter_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HunterArmorItem animatable) {
        return new ResourceLocation(Frostwork.MOD_ID, "textures/models/armor/hunter_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HunterArmorItem animatable) {
        return new ResourceLocation(Frostwork.MOD_ID, "animations/hunter_armor.animation.json");
    }
}
