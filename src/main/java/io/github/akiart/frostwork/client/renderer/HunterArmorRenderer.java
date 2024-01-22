package io.github.akiart.frostwork.client.renderer;

import io.github.akiart.frostwork.client.model.HunterArmorModel;
import io.github.akiart.frostwork.common.item.itemTypes.HunterArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class HunterArmorRenderer extends GeoArmorRenderer<HunterArmorItem> {
    public HunterArmorRenderer() {
        super(new HunterArmorModel());
    }
}
