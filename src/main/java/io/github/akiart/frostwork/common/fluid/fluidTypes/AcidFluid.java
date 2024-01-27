package io.github.akiart.frostwork.common.fluid.fluidTypes;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.akiart.frostwork.Consts;
import io.github.akiart.frostwork.FUtil;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.fluid.FFluidTypes;
import io.github.akiart.frostwork.common.fluid.FFluids;
import io.github.akiart.frostwork.common.item.FItems;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class AcidFluid extends FluidType {
    public AcidFluid(Properties properties) {
        super(properties);
    }

    public static BaseFlowingFluid.Properties getProperties() {

        return new BaseFlowingFluid.Properties(FFluidTypes.ACID, FFluids.ACID_SOURCE, FFluids.ACID_FLOWING)
                .bucket(FItems.ACID_BUCKET)
                .slopeFindDistance(4)
                .levelDecreasePerBlock(2)
                .tickRate(5)
                .block(FBlocks.ACID);
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return Consts.COLORS.ACID_GREEN;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return new ResourceLocation(Frostwork.MOD_ID, "block/acid_flow");
            }

            @Override
            public ResourceLocation getStillTexture() {
                return new ResourceLocation(Frostwork.MOD_ID, "block/acid_still");
            }

            @Override
            public @NotNull ResourceLocation getOverlayTexture() {
                return new ResourceLocation(Frostwork.MOD_ID, "misc/acid_overlay");
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return FUtil.Color.toVector(Consts.COLORS.ACID_GREEN);
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(0.25f);
                RenderSystem.setShaderFogEnd(8f);
            }
        });
    }
}
