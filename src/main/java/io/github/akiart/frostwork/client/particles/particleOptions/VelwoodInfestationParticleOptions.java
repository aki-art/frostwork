package io.github.akiart.frostwork.client.particles.particleOptions;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.client.particles.FParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public final class VelwoodInfestationParticleOptions extends ParticleType<VelwoodInfestationParticleOptions> implements ParticleOptions {

    public static final Codec<VelwoodInfestationParticleOptions> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                            Vec3.CODEC.fieldOf("orbit_delta").forGetter(particleOptions -> particleOptions.orbitModifier))
                    .apply(builder, VelwoodInfestationParticleOptions::new)
    );

    public static final Deserializer<VelwoodInfestationParticleOptions> DESERIALIZER = new Deserializer<>() {
        public @NotNull VelwoodInfestationParticleOptions fromCommand(@NotNull ParticleType<VelwoodInfestationParticleOptions> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double x = reader.readDouble();
            reader.expect(' ');
            double y = reader.readDouble();
            reader.expect(' ');
            double z = reader.readDouble();
            return new VelwoodInfestationParticleOptions(new Vec3(x, y, z));
        }

        public @NotNull VelwoodInfestationParticleOptions fromNetwork(@NotNull ParticleType<VelwoodInfestationParticleOptions> type, FriendlyByteBuf buffer) {
            return new VelwoodInfestationParticleOptions(buffer.readVec3());
        }
    };
    private final Vec3 orbitModifier;

    public VelwoodInfestationParticleOptions(Vec3 orbitModifier) {
        super(false, DESERIALIZER);
        this.orbitModifier = orbitModifier;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return FParticles.VELMITE_INFECTION.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeVec3(this.orbitModifier);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format(Locale.ROOT, "%s %s", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.orbitModifier);
    }

    public Vec3 orbitModifier() {
        return orbitModifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (VelwoodInfestationParticleOptions) obj;
        return Objects.equals(this.orbitModifier, that.orbitModifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orbitModifier);
    }

    @Override
    public String toString() {
        return "VelwoodInfestationParticleOptions[" +
                "orbitModifier=" + orbitModifier + ']';
    }

    @Override
    public Codec<VelwoodInfestationParticleOptions> codec() {
        return CODEC;
    }
}
