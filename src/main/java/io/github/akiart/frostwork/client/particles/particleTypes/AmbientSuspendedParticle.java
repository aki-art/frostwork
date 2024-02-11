package io.github.akiart.frostwork.client.particles.particleTypes;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

/** @see SuspendedParticle */
public class AmbientSuspendedParticle extends TextureSheetParticle {

    private int offset;

    protected AmbientSuspendedParticle(ClientLevel level, SpriteSet sprites, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setSize(0.01F, 0.01F);
        this.pickSprite(sprites);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
        this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = false;
        this.friction = 1F;
        this.gravity = 0.0F;
        this.offset = level.random.nextInt(0, 40);

        this.xd = (xSpeed + (Math.random() * 2.0 - 1.0)) * 0.01F;
        this.yd = (ySpeed + (Math.random() * 2.0 - 1.0)) * 0.01F;
        this.zd = (zSpeed + (Math.random() * 2.0 - 1.0)) * 0.01F;
    }

    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;

        if (age++ >= lifetime) {
            remove();
            return;
        }

        move(xd, yd, zd);

        var fade = (Mth.sin((float)(age + offset) * 0.1f) / 2f) + 0.5f;
        setAlpha(fade);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class GrimSporeProvider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public GrimSporeProvider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

            float speed = 0.0001f;
            ySpeed = (double)level.random.nextFloat() * -0.4 * level.random.nextFloat() * speed+ 0.4f;
            xSpeed = (double)level.random.nextFloat() * -0.4 * level.random.nextFloat() * speed ;
            zSpeed = (double)level.random.nextFloat() * -0.4 * level.random.nextFloat() * speed;
            AmbientSuspendedParticle suspendedparticle = new AmbientSuspendedParticle(level, this.sprite, x, y, z, xSpeed, ySpeed, zSpeed);
            suspendedparticle.setColor(1F, 0.8F, 0.4F);
            suspendedparticle.setSize(0.003F, 0.003F);
            suspendedparticle.friction = 1f;

            suspendedparticle.setLifetime(level.random.nextInt(50) + 100);
            return suspendedparticle;
        }

    }
}
