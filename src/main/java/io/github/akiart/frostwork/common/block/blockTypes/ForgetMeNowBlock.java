package io.github.akiart.frostwork.common.block.blockTypes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.common.effects.FEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import java.util.List;

public class ForgetMeNowBlock extends FlowerBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final MapCodec<ForgetMeNowBlock> CODEC = RecordCodecBuilder
            .mapCodec((builder) -> builder
                    .group(EFFECTS_FIELD
                            .forGetter(FlowerBlock::getSuspiciousEffects), propertiesCodec())
                    .apply(builder, ForgetMeNowBlock::new));


    public MapCodec<ForgetMeNowBlock> codec() {
        return CODEC;
    }

    public ForgetMeNowBlock(MobEffect effect, int ticks, BlockBehaviour.Properties properties) {
        this(makeEffectList(effect, ticks), properties);
    }


    public ForgetMeNowBlock(List<EffectEntry> effects, BlockBehaviour.Properties properties) {
        super(effects, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity livingentity) {
            livingentity.addEffect(new MobEffectInstance(FEffects.FRAIL.get(), 40));
        }
    }
}
