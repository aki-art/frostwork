package io.github.akiart.frostwork.client.world;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import io.github.akiart.frostwork.Frostwork;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

// todo - this seems to have changed significantly since 1.16
@OnlyIn(Dist.CLIENT)
public class FantasiaDimensionRenderInfo extends DimensionSpecialEffects {
    public static final float CLOUD_HEIGHT = 430;
    private float angle;

    private VertexBuffer starBuffer;
    private VertexBuffer skyBuffer;

    private static final ResourceLocation SKY_LEFT = new ResourceLocation(Frostwork.MOD_ID, "textures/environment/sky.png");
    private static final ResourceLocation BIG_MOON_LOCATION = new ResourceLocation(Frostwork.MOD_ID, "textures/environment/moon1.png");

    public static ResourceLocation KEY = new ResourceLocation(Frostwork.MOD_ID, "fantasia_effects");

    public FantasiaDimensionRenderInfo() {
        super(CLOUD_HEIGHT, true, SkyType.NONE, false, false);
        createStars();
        skyBuffer = Minecraft.getInstance().levelRenderer.skyBuffer;
    }

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 fogColor, float dayLight) {
        dayLight = Math.max(0.2f, dayLight);
        return fogColor.multiply((dayLight * 0.94F + 0.06F), dayLight * 0.94F + 0.06F, dayLight * 0.91F + 0.09F);
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }

    private void createStars() {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (this.starBuffer != null) {
            this.starBuffer.close();
        }

        this.starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.drawStars(bufferbuilder);
        this.starBuffer.bind();
        this.starBuffer.upload(bufferbuilder$renderedbuffer);
        VertexBuffer.unbind();
    }

    private BufferBuilder.RenderedBuffer drawStars(BufferBuilder pBuilder) {
        RandomSource randomsource = RandomSource.create(10842L);
        pBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for(int i = 0; i < 1500; ++i) {
            double d0 = (double)(randomsource.nextFloat() * 2.0F - 1.0F);
            double d1 = (double)(randomsource.nextFloat() * 2.0F - 1.0F);
            double d2 = (double)(randomsource.nextFloat() * 2.0F - 1.0F);
            double d3 = (double)(0.15F + randomsource.nextFloat() * 0.1F);
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d4 < 1.0 && d4 > 0.01) {
                d4 = 1.0 / Math.sqrt(d4);
                d0 *= d4;
                d1 *= d4;
                d2 *= d4;
                double d5 = d0 * 100.0;
                double d6 = d1 * 100.0;
                double d7 = d2 * 100.0;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = randomsource.nextDouble() * Math.PI * 2.0;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);

                for(int j = 0; j < 4; ++j) {
                    double d17 = 0.0;
                    double d18 = (double)((j & 2) - 1) * d3;
                    double d19 = (double)((j + 1 & 2) - 1) * d3;
                    double d20 = 0.0;
                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12 + 0.0 * d13;
                    double d24 = 0.0 * d12 - d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    pBuilder.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
                }
            }
        }

        return pBuilder.end();
    }

    private void renderVanillaSky(PoseStack poseStack, ClientLevel level, Camera camera, float partialTick, Matrix4f projectionMatrix) {
        Vec3 vec3 = level.getSkyColor(camera.getPosition(), partialTick);
        float f = (float)vec3.x;
        float f1 = (float)vec3.y;
        float f2 = (float)vec3.z;
        FogRenderer.levelFogColor();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.depthMask(false);
        RenderSystem.setShaderColor(f, f1, f2, 1.0F);
        ShaderInstance shaderinstance = RenderSystem.getShader();
        this.skyBuffer.bind();
        this.skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderinstance);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();
        float[] afloat = level.effects().getSunriseColor(level.getTimeOfDay(partialTick), partialTick);
        if (afloat != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.pushPose();
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            float f3 = Mth.sin(level.getSunAngle(partialTick)) < 0.0F ? 180.0F : 0.0F;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f3));
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            float f4 = afloat[0];
            float f5 = afloat[1];
            float f6 = afloat[2];
            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
            int i = 16;

            for(int j = 0; j <= 16; ++j) {
                float f7 = (float)j * (float) (Math.PI * 2) / 16.0F;
                float f8 = Mth.sin(f7);
                float f9 = Mth.cos(f7);
                bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3])
                        .color(afloat[0], afloat[1], afloat[2], 0.0F)
                        .endVertex();
            }

            BufferUploader.drawWithShader(bufferbuilder.end());
            poseStack.popPose();
        }
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {

        setupFog.run();

        float rainLevel = 1.0F - level.getRainLevel(partialTick);
        float starBrightness = level.getStarBrightness(partialTick) * rainLevel;

        drawNormalSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy);
        renderVanillaSky(poseStack, level, camera, partialTick, projectionMatrix);

        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, SKY_LEFT);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();


        //renderSkyBox(poseStack, bufferbuilder, tesselator);
        var lastMatrix = poseStack.last().pose();

        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
        );

        // test second skybox for some layered nebulas
        poseStack.pushPose();

        poseStack.mulPose(Axis.ZP.rotationDegrees(90));
        poseStack.mulPose(Axis.XP.rotationDegrees(angle % 360));
        poseStack.mulPose(Axis.YP.rotationDegrees(angle % 360));
       // angle += 0.005f;

        renderSkyBox(poseStack, bufferbuilder, tesselator, starBrightness);
        poseStack.popPose();


        // test moon
        drawBigMoon(poseStack, level, ticks, projectionMatrix, bufferbuilder, lastMatrix, 30f);

        renderStars(level, rainLevel, starBrightness, setupFog, partialTick, poseStack, projectionMatrix);

        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();

        return true;
    }

    private void renderStars(ClientLevel level, float rainLevel, float starBrightness, Runnable setupFog, float partialTick, PoseStack poseStack, Matrix4f projectionMatrix) {

        if (starBrightness > 0.0F) {
            RenderSystem.setShaderColor(starBrightness, starBrightness, starBrightness, starBrightness);
            FogRenderer.setupNoFog();
            this.starBuffer.bind();
            this.starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
            VertexBuffer.unbind();
            setupFog.run();
        }
    }

    private void drawNormalSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy) {
        var renderer = Minecraft.getInstance().levelRenderer;

    }

    private static void renderSkyBox(PoseStack poseStack, BufferBuilder bufferbuilder, Tesselator tesselator, float starBrightness) {

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        for(int i = 0; i < 6; ++i) {
            poseStack.pushPose();

            switch (i) {
                case 1 -> poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                case 2 -> poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                case 3 -> poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                case 4 -> poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                case 5 -> poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }

            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

            var sixth = 1/6f;
            var left = sixth * i;
            var right = sixth * i + sixth;

            var lightness = 255;

            bufferbuilder
                    .vertex(matrix4f, -100.0F, -100.0F, -100.0F)
                    .uv(left, 0) // 1 1
                    .color(lightness, lightness, lightness, 255)
                    .endVertex();

            bufferbuilder
                    .vertex(matrix4f, -100.0F, -100.0F, 100.0F)
                    .uv(left, 1) // 1 2
                    .color(lightness, lightness, lightness, 255)
                    .endVertex();

            bufferbuilder
                    .vertex(matrix4f, 100.0F, -100.0F, 100.0F)
                    .uv(right, 1) // 2 2
                    .color(lightness, lightness, lightness, 255)
                    .endVertex();

            bufferbuilder
                    .vertex(matrix4f, 100.0F, -100.0F, -100.0F)
                    .uv(right, 0) // 2 1
                    .color(lightness, lightness, lightness, 255)
                    .endVertex();

            tesselator.end();
            poseStack.popPose();
        }
    }

    private void drawBigMoon(PoseStack poseStack, ClientLevel level, int ticks, Matrix4f matrixStack, BufferBuilder bufferbuilder, Matrix4f matrix4f1, float size) {

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(ticks) * 360.0F));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, BIG_MOON_LOCATION);

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(matrix4f1, -size, 100.0F, -size).uv(0.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, size, 100.0F, -size).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, size, 100.0F, size).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f1, -size, 100.0F, size).uv(0.0F, 1.0F).endVertex();

        BufferUploader.drawWithShader(bufferbuilder.end());
        poseStack.popPose();
    }
}
