package io.github.akiart.frostwork.client.particles.particleTypes;

import io.github.akiart.frostwork.Consts;
import io.github.akiart.frostwork.FUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AcidBubbleParticle extends TextureSheetParticle {
    private static final float ACID_R = (float) FUtil.Color.getR(Consts.COLORS.ACID_BLUE_TINT);
    private static final float ACID_G = (float)FUtil.Color.getG(Consts.COLORS.ACID_BLUE_TINT);
    private static final float ACID_B = (float)FUtil.Color.getB(Consts.COLORS.ACID_BLUE_TINT);
    private static float HORIZONTAL_ACCELERATION = 1.02f;
    private final SpriteSet sprites;
    boolean hasReachedSurface = false;
    boolean markedToPop = false;
    int popFrame = 0;

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
    protected AcidBubbleParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ, SpriteSet sprites) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.sprites = sprites;

        scale(1F);
        lifetime = 400;

        xd = motionX;
        yd = motionY;
        zd = motionZ;

        hasPhysics = false;

        setColor(ACID_R, ACID_G, ACID_B);
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;

        if (lifetime-- <= 0) {
            remove();
            return;
        }

        move(xd, yd, zd);

        xd *= 0.95f;
        zd *= 0.95f;

        BlockPos blockpos = new BlockPos((int)x, (int)y, (int)z);
        FluidState state = level.getFluidState(blockpos);
        FluidState stateAbove = level.getFluidState(blockpos.above());

        if (!hasReachedSurface && stateAbove.isEmpty()) {
            double h = blockpos.getY() + state.getHeight(level, blockpos);
            if(y >= h){
                y = h;
                yd *= 0.1f;
                hasReachedSurface = true;
            }
            else {
                yd *= 1.02f;
            }
        }
        else if(hasReachedSurface) {
            xd *= 0.8f;
            zd *= 0.8f;
            yd *= 0.7f;
            if(markedToPop) {
                setSprite(sprites.get(popFrame++, 4));
                if(popFrame >= 5) remove();
            }
            else markedToPop = random.nextBoolean();
        }
        else {
            yd *= 1.02f;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

            AcidBubbleParticle particle = new AcidBubbleParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
            particle.setSpriteFromAge(sprites);
            return particle;
        }
    }
}
