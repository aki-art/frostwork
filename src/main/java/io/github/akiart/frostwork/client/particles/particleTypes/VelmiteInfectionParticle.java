package io.github.akiart.frostwork.client.particles.particleTypes;

import io.github.akiart.frostwork.Consts;
import io.github.akiart.frostwork.FUtil;
import io.github.akiart.frostwork.client.particles.particleOptions.VelwoodInfestationParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

public class VelmiteInfectionParticle extends TextureSheetParticle {
    private static final float R = (float)FUtil.Color.getR(Consts.COLORS.VELMITE);
    private static final float G = (float)FUtil.Color.getG(Consts.COLORS.VELMITE);
    private static final float B = (float)FUtil.Color.getB(Consts.COLORS.VELMITE);
    private final Vector3d origin;
    private Vector3d current = new Vector3d(0, 0, 0);
    private Vector3d orbitSpeed;

    protected VelmiteInfectionParticle(ClientLevel pLevel, double originX, double originY, double originZ,
                                       double x, double y, double z, Vec3 orbitModifier) { //, double targetXSpeed, double targetYSpeed, double targetZSpeed) {
        super(pLevel, originX, originY, originZ);

        this.x = x;
        this.y = y;
        this.z = z;

        this.quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;
        this.lifetime = 20 * 5;

        this.xd = x;
        this.yd = y;
        this.zd = z;

        origin = new Vector3d(originX, originY, originZ);
        orbitSpeed = new Vector3d(orbitModifier.x, orbitModifier.y, orbitModifier.z);

        hasPhysics = false;
        setColor(R, G, B);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (age++ > lifetime) {
            remove();
            return;
        }

        current = current.set(x, y, z);
        Vector3d vec = current.sub(origin);
        vec = vec.normalize();
        vec = vec.cross(orbitSpeed);

        //orbitSpeed = orbitSpeed.mul(0.99);


        xd = vec.x;
        yd = vec.y;
        zd = vec.z;

        move( xd,yd,zd);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<VelwoodInfestationParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(VelwoodInfestationParticleOptions type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

            VelmiteInfectionParticle particle = new VelmiteInfectionParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, type.orbitModifier());
            particle.setSpriteFromAge(sprites);

            return particle;
        }
    }
}
