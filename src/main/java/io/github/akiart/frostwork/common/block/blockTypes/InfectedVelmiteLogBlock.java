package io.github.akiart.frostwork.common.block.blockTypes;

import io.github.akiart.frostwork.client.particles.particleOptions.VelwoodInfestationParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;

public class InfectedVelmiteLogBlock extends Block {

    public static DirectionProperty EXPOSED_FACE = DirectionProperty.create(
            "exposed_face", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN
    );

    public InfectedVelmiteLogBlock(Properties properties) {
        super(properties);

    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!world.isClientSide()) return;

        spawnInfectionParticle(world, pos, random);
        spawnInfectionParticle(world, pos, random);
        spawnInfectionParticle(world, pos, random);
    }


    private static void spawnInfectionParticle(Level world, BlockPos pos, RandomSource random) {
        // todo: from exposed faces only
        double xOffset = random.nextDouble();
        double yOffset = random.nextDouble();
        double zOffset = random.nextDouble();

        double originX = (double) pos.getX() + 0.5f;
        double originY = (double) pos.getY() + 0.5f;
        double originZ = (double) pos.getZ() + 0.5f;

        double speedMod = 0.05f;

        double x = (double) pos.getX() - 1 + xOffset * 3;
        double y = (double) pos.getY() - 1 + xOffset * 3;
        double z = (double) pos.getZ() - 1 + xOffset * 3;

        Vec3 orbitModifier = new Vec3(
                getRandomOrbitModifier(random),
                getRandomOrbitModifier(random),
                getRandomOrbitModifier(random)
        );

        world.addParticle(new VelwoodInfestationParticleOptions(orbitModifier), originX, originY, originZ, x, y, z);
    }

    private static double getRandomOrbitModifier(RandomSource random) {
        double num = random.nextDouble();
        num += 0.33f;
        num *= random.nextBoolean() ? 1 : -1;
        num *= 0.14f;

        return num;
    }
}
