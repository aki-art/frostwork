//package io.github.akiart.frostwork.client.particles.particleTypes;
//
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.client.particle.Particle;
//import net.minecraft.client.particle.ParticleProvider;
//import net.minecraft.client.particle.SpriteSet;
//import net.minecraft.client.particle.SuspendedParticle;
//import net.minecraft.core.particles.SimpleParticleType;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.api.distmarker.OnlyIn;
//
//public class GrimSporeParticle {
//    @OnlyIn(Dist.CLIENT)
//    public static class Provider implements ParticleProvider<SimpleParticleType> {
//        private final SpriteSet sprite;
//
//        public Provider(SpriteSet pSprites) {
//            this.sprite = pSprites;
//        }
//
//        public Particle createParticle(
//                SimpleParticleType pType,
//                ClientLevel pLevel,
//                double pX,
//                double pY,
//                double pZ,
//                double pXSpeed,
//                double pYSpeed,
//                double pZSpeed
//        ) {
//            double ySpeed = (double)pLevel.random.nextFloat() * -1.9 * (double)pLevel.random.nextFloat() * 0.1;
//            SuspendedParticle suspendedparticle = new SuspendedParticle(pLevel, this.sprite, pX, pY, pZ, 0.0, ySpeed, 0.0);
//            suspendedparticle.setColor(0.76F, 0.57F, 0.3F);
//            suspendedparticle.setSize(0.001F, 0.001F);
//            return suspendedparticle;
//        }
//    }
//}
