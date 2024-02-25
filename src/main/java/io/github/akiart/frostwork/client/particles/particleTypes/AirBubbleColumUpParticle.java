package io.github.akiart.frostwork.client.particles.particleTypes;

import io.github.akiart.frostwork.common.block.FBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AirBubbleColumUpParticle extends TextureSheetParticle {
    public AirBubbleColumUpParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ);
        this.gravity = -0.125F;
        this.friction = 0.85F;
        this.setSize(0.02F, 0.02F);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
        this.xd = pXSpeed * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.yd = pYSpeed * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.zd = pZSpeed * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.lifetime = (int)(40.0 / (Math.random() * 0.8 + 0.2)) * 2;
    }

    @Override
    public void tick() {
        super.tick();
        var block = this.level.getBlockState(BlockPos.containing(this.x, this.y, this.z));
        if (!this.removed && !block.isAir() && !block.is(FBlocks.AIR_BUBBLE_COLUMN)) {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(
                SimpleParticleType pType,
                ClientLevel pLevel,
                double pX,
                double pY,
                double pZ,
                double pXSpeed,
                double pYSpeed,
                double pZSpeed
        ) {
            AirBubbleColumUpParticle bubblecolumnupparticle = new AirBubbleColumUpParticle(
                    pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed
            );
            bubblecolumnupparticle.pickSprite(this.sprite);
            return bubblecolumnupparticle;
        }
    }
}
