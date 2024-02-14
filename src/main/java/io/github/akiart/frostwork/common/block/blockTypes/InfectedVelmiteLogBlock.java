package io.github.akiart.frostwork.common.block.blockTypes;

import io.github.akiart.frostwork.client.particles.particleOptions.VelwoodInfestationParticleOptions;
import io.github.akiart.frostwork.common.item.FItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class InfectedVelmiteLogBlock extends Block {
    public static BooleanProperty ACTIVE = BooleanProperty.create("active");

    public InfectedVelmiteLogBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!world.isClientSide() || !state.getValue(ACTIVE)) return;

        spawnInfectionParticle(world, pos, random);
        spawnInfectionParticle(world, pos, random);
        spawnInfectionParticle(world, pos, random);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(!state.getValue(ACTIVE) && random.nextInt(4) == 1)
            level.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {

        if (level.isClientSide)
            return InteractionResult.SUCCESS;

        if(blockState.getValue(ACTIVE)) {
            ItemStack itemstack = player.getItemInHand(hand);

            if (itemstack.is(Items.GLASS_BOTTLE)) {
                itemstack.shrink(1);

                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 0.5F);
                Item bottle = FItems.SAP_O_MITE_BOTTLE.get();

                level.setBlock(pos, blockState.setValue(ACTIVE, false), Block.UPDATE_CLIENTS);

                if (itemstack.isEmpty()) {
                    player.setItemInHand(hand, new ItemStack(bottle));
                } else if (!player.getInventory().add(new ItemStack(bottle))) {
                    player.drop(new ItemStack(bottle), false);
                }


                player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
                level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }

    private static void spawnInfectionParticle(Level world, BlockPos pos, RandomSource random) {
        double xOffset = random.nextDouble();
        double yOffset = random.nextDouble();
        double zOffset = random.nextDouble();

        double originX = (double) pos.getX() + 0.5f;
        double originY = (double) pos.getY() + 0.5f;
        double originZ = (double) pos.getZ() + 0.5f;

        double x = (double) pos.getX() - 1 + xOffset * 3;
        double y = (double) pos.getY() - 1 + yOffset * 3;
        double z = (double) pos.getZ() - 1 + zOffset * 3;

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
