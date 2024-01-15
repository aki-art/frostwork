package io.github.akiart.frostwork.client.renderer.entityRenderer;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.entity.entityTypes.WurmArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class WurmArrowRenderer extends ArrowRenderer<WurmArrow>  {
    public static final ResourceLocation WURM_ARROW_LOCATION = new ResourceLocation(Frostwork.MOD_ID, "textures/entity/projectiles/tatzelwurm_arrow.png");

    public WurmArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(WurmArrow pEntity) {
        return WURM_ARROW_LOCATION;
    }
}
