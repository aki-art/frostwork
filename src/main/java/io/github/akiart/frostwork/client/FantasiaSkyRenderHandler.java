//package io.github.akiart.frostwork.client;
//
//
//import com.mojang.blaze3d.platform.GlStateManager;
//import com.mojang.blaze3d.systems.RenderSystem;
//import com.mojang.blaze3d.vertex.BufferBuilder;
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.Tesselator;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.client.renderer.texture.TextureManager;
//import net.minecraft.resources.ResourceLocation;
//import org.joml.Matrix4f;
//import org.joml.Vector3d;
//
//@SuppressWarnings("deprecation")
//public class FantasiaSkyRenderHandler {
//    // private static final ResourceLocation MOON1 = new ResourceLocation(Fantasia.ID, "textures/environment/moon1.png");
//    private static final ResourceLocation MOON2 = new ResourceLocation(Fantasia.ID, "textures/environment/moon2.png");
//    private static final ResourceLocation TOP = new ResourceLocation(Fantasia.ID, "textures/environment/top.png");
//    private static final ResourceLocation BACK = new ResourceLocation(Fantasia.ID, "textures/environment/back.png");
//    private static final ResourceLocation RIGHT = new ResourceLocation(Fantasia.ID, "textures/environment/right.png");
//    private static final ResourceLocation LEFT = new ResourceLocation(Fantasia.ID, "textures/environment/left.png");
//    private static final ResourceLocation FRONT = new ResourceLocation(Fantasia.ID, "textures/environment/front.png");
//    private static final ResourceLocation BOTTOM = new ResourceLocation(Fantasia.ID, "textures/environment/bottom.png");
//    private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("textures/environment/moon_phases.png");
//    private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("textures/environment/sun.png");
//
//    private static float SUN_SIZE = 35f;
//    private static float MOON1_SIZE = 33f;
//    private static float MOON2_SIZE = 100f;
//
//    private Stars stars;
//    private Nebula nebula;
//
//    private static final float SKY_DEPTH = 100f;
//
//    private TextureManager textureManager;
//    private Minecraft mc;
//
//    public FantasiaSkyRenderHandler() {
//        super();
//
//        mc = Minecraft.getInstance();
//        this.textureManager = mc.textureManager;
//
//        stars = new Stars();
//        nebula = new Nebula(textureManager);
//    }
//
//    @Override
//    public void render(int ticks, float partialTicks, PoseStack poseStack, Matrix4f matrixStack, ClientLevel world, Minecraft mc) {
//
//        RenderSystem.disableTexture();
//
//        Vector3d color = world.getSkyColor(mc.gameRenderer.getActiveRenderInfo().getBlockPos(), partialTicks);
//
//        float r = (float)color.x;
//        float g = (float)color.y;
//        float b = (float)color.z;
//
//        // FogRenderer.applyFog();
//        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
//
//        RenderSystem.depthMask(false);
//        RenderSystem.enableFog();
//        RenderSystem.color3f(r, g, b);
//
//        mc.worldRenderer.skyVBO.bindBuffer();
//        mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
//        mc.worldRenderer.skyVBO.draw(matrixStack.getLast().getMatrix(), 7);
//
//        VertexBuffer.unbindBuffer();
//        mc.worldRenderer.skyVertexFormat.clearBufferState();
//
//        RenderSystem.disableFog();
//        RenderSystem.disableAlphaTest();
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//
//        float[] afloat = world.getDimensionRenderInfo().func_230492_a_(world.func_242415_f(partialTicks), partialTicks);
//
//        if (afloat != null) {
//
//            RenderSystem.disableTexture();
//            RenderSystem.shadeModel(7425);
//
//            matrixStack.push();
//            matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
//            float angle = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F;
//            matrixStack.rotate(Vector3f.ZP.rotationDegrees(angle));
//            matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
//
//            float r2 = afloat[0];
//            float g2 = afloat[1];
//            float b2 = afloat[2];
//
//            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
//
//            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
//            bufferbuilder.pos(matrix4f, 0.0F, 100.0F, 0.0F).color(r2, g2, b2, afloat[3]).endVertex();
//            int i = 16;
//
//            for (int j = 0; j <= 16; ++j) {
//                float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
//                float f8 = MathHelper.sin(f7);
//                float f9 = MathHelper.cos(f7);
//                bufferbuilder.pos(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(r2, g2, b2, 0.0F).endVertex();
//            }
//
//            bufferbuilder.finishDrawing();
//            WorldVertexBufferUploader.draw(bufferbuilder);
//            matrixStack.pop();
//            RenderSystem.shadeModel(7424);
//
//        }
//
//
//        RenderSystem.enableTexture();
//        RenderSystem.blendFuncSeparate(
//                GlStateManager.SourceFactor.SRC_ALPHA,
//                GlStateManager.DestFactor.ONE,
//                GlStateManager.SourceFactor.ONE,
//                GlStateManager.DestFactor.ZERO);
//
//        matrixStack.push();
//
//
//        float rainAlpha = 1.0F - world.getRainStrength(partialTicks);
//        RenderSystem.color4f(1.0F, 1.0F, 1.0F, rainAlpha);
//
//        matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0F));
//        matrixStack.rotate(Vector3f.XP.rotationDegrees(world.func_242415_f(partialTicks) * 360.0F));
//
//        Matrix4f matrix4f1 = matrixStack.getLast().getMatrix();
//
//        drawSun(bufferbuilder, matrix4f1, SUN_SIZE);
//        drawSmallMoon(world, bufferbuilder, matrix4f1, MOON1_SIZE);
//        drawBigMoon(matrixStack, bufferbuilder, matrix4f1, MOON2_SIZE);
//
//        //renderNightSky2(matrixStack, bufferbuilder, matrix4f1);
//
//        RenderSystem.disableTexture();
//
//        drawStars(partialTicks, matrixStack, world, mc, rainAlpha);
//
//        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.disableBlend();
//        RenderSystem.enableAlphaTest();
//        RenderSystem.enableFog();
//        matrixStack.pop();
//        RenderSystem.disableTexture();
//        RenderSystem.color3f(0.0F, 0.0F, 0.0F);
//
//        double d0 = mc.player.getEyePosition(partialTicks).y - world.getWorldInfo().getVoidFogHeight();
//
//        if (d0 < 0.0D) {
//            matrixStack.push();
//            matrixStack.translate(0.0D, 12.0D, 0.0D);
//
//            mc.worldRenderer.sky2VBO.bindBuffer();
//            mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
//            mc.worldRenderer.sky2VBO.draw(matrixStack.getLast().getMatrix(), 7);
//            VertexBuffer.unbindBuffer();
//            mc.worldRenderer.skyVertexFormat.clearBufferState();
//            matrixStack.pop();
//        }
//
//        if (world.getDimensionRenderInfo().func_239216_b_())
//            RenderSystem.color3f(r * 0.2F + 0.04F, g * 0.2F + 0.04F, b * 0.6F + 0.1F);
//        else
//            RenderSystem.color3f(r, g, b);
//
//
//
//        RenderSystem.enableTexture();
//        RenderSystem.depthMask(true);
//        RenderSystem.disableFog();
//
////		nebula.nebulaVBO.bindBuffer();
////		mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
////		nebula.nebulaVBO.draw(matrixStack.getLast().getMatrix(), 7);
////		nebula.renderNebula(bufferbuilder, matrixStack);
////		VertexBuffer.unbindBuffer();
////		mc.worldRenderer.skyVertexFormat.clearBufferState();
//
//    }
//
//    private void drawBigMoon(MatrixStack matrixStack, BufferBuilder bufferbuilder, Matrix4f matrix4f1, float size) {
//        matrixStack.rotate(Vector3f.ZP.rotationDegrees(-200.0F));
//        matrixStack.rotate(Vector3f.XP.rotationDegrees(-20.0F));
//        this.textureManager.bindTexture(MOON2);
//        size = MOON2_SIZE;
//        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//        bufferbuilder.pos(matrix4f1, -size, SKY_DEPTH, -size).tex(0.0F, 0.0F).endVertex();
//        bufferbuilder.pos(matrix4f1, size, SKY_DEPTH, -size).tex(1.0F, 0.0F).endVertex();
//        bufferbuilder.pos(matrix4f1, size, SKY_DEPTH, size).tex(1.0F, 1.0F).endVertex();
//        bufferbuilder.pos(matrix4f1, -size, SKY_DEPTH, size).tex(0.0F, 1.0F).endVertex();
//        bufferbuilder.finishDrawing();
//        WorldVertexBufferUploader.draw(bufferbuilder);;
//    }
//
//    private void drawSmallMoon(ClientWorld world, BufferBuilder bufferbuilder, Matrix4f matrix4f1, float size) {
//        this.textureManager.bindTexture(MOON_PHASES_TEXTURES);
//        int k = world.getMoonPhase();
//        int l = k % 4;
//        int i1 = k / 4 % 2;
//
//        float f13 = (float) (l + 0) / 4.0F;
//        float f14 = (float) (i1 + 0) / 2.0F;
//        float f15 = (float) (l + 1) / 4.0F;
//        float f16 = (float) (i1 + 1) / 2.0F;
//
//        size = MOON1_SIZE;
//        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//        bufferbuilder.pos(matrix4f1, -size, -SKY_DEPTH, size).tex(f15, f16).endVertex();
//        bufferbuilder.pos(matrix4f1, size, -SKY_DEPTH, size).tex(f13, f16).endVertex();
//        bufferbuilder.pos(matrix4f1, size, -SKY_DEPTH, -size).tex(f13, f14).endVertex();
//        bufferbuilder.pos(matrix4f1, -size, -SKY_DEPTH, -size).tex(f15, f14).endVertex();
//        bufferbuilder.finishDrawing();
//        WorldVertexBufferUploader.draw(bufferbuilder);
//    }
//
//    private void drawSun(BufferBuilder bufferbuilder, Matrix4f matrix4f, float size) {
//        this.textureManager.bindTexture(SUN_TEXTURES);
//
//        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//        bufferbuilder.pos(matrix4f, -size, SKY_DEPTH, -size).tex(0.0F, 0.0F).endVertex();
//        bufferbuilder.pos(matrix4f, size, SKY_DEPTH, -size).tex(1.0F, 0.0F).endVertex();
//        bufferbuilder.pos(matrix4f, size, SKY_DEPTH, size).tex(1.0F, 1.0F).endVertex();
//        bufferbuilder.pos(matrix4f, -size, SKY_DEPTH, size).tex(0.0F, 1.0F).endVertex();
//        bufferbuilder.finishDrawing();
//        WorldVertexBufferUploader.draw(bufferbuilder);
//    }
//
//    private void drawStars(float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc,
//                           float rainAlpha) {
//        float starBrightness = world.getStarBrightness(partialTicks) * rainAlpha;
//        if (starBrightness > 0.0F) {
//            RenderSystem.color4f(starBrightness, starBrightness, starBrightness, starBrightness);
//            stars.starVBO.bindBuffer();
//            mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
//            stars.starVBO.draw(matrixStack.getLast().getMatrix(), 7);
//            VertexBuffer.unbindBuffer();
//            mc.worldRenderer.skyVertexFormat.clearBufferState();
//        }
//    }
//
//
//
//    float size = 4096f / 4f;
//    private void renderNightSky(MatrixStack matrixStack) {
//
//        RenderSystem.disableAlphaTest();
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.depthMask(false);
//
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//
//        float u = 0;
//        float v = 0;
//
//        for (int i = 0; i < 6; ++i) {
//
//            matrixStack.push();
//
//            if (i == 0) {
//                this.textureManager.bindTexture(BOTTOM);
//                u = size;
//                v = 2 * size;
//            }
//
//            else if (i == 1) {
//                matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
//                this.textureManager.bindTexture(LEFT);
//                u = size;
//                v = 0;
//            }
//
//            else if (i == 2) {
//                matrixStack.rotate(Vector3f.XP.rotationDegrees(-90.0F));
//                this.textureManager.bindTexture(RIGHT);
//                u = 0;
//                v = size;
//            }
//
//            else if (i == 3) {
//                matrixStack.rotate(Vector3f.XP.rotationDegrees(180.0F));
//                this.textureManager.bindTexture(TOP);
//                u = size;
//                v = size;
//            }
//
//            else if (i == 4) {
//                matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
//                this.textureManager.bindTexture(FRONT);
//                u = 2 * size;
//                v = size;
//            }
//
//            else if (i == 5) {
//                matrixStack.rotate(Vector3f.ZP.rotationDegrees(-90.0F));
//                this.textureManager.bindTexture(BACK);
//                u = 3 * size;
//                v = size;
//
//            }
//
//            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
//
//            float shade = 255;
//            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//            bufferbuilder.pos(matrix4f, -100.0F, -100.0F, -100.0F).tex(0, 0).color(shade, shade, shade, 255).endVertex();
//            bufferbuilder.pos(matrix4f, -100.0F, -100.0F, 100.0F).tex(0, 1).color(shade, shade, shade, 255).endVertex();
//            bufferbuilder.pos(matrix4f, 100.0F, -100.0F, 100.0F).tex(1, 1).color(shade, shade, shade, 255).endVertex();
//            bufferbuilder.pos(matrix4f, 100.0F, -100.0F, -100.0F).tex(1, 0).color(shade, shade, shade, 255).endVertex();
//
//            tessellator.draw();
//            matrixStack.pop();
//        }
//
//        //WorldVertexBufferUploader.draw(bufferbuilder);
//
//    }
//
//}
