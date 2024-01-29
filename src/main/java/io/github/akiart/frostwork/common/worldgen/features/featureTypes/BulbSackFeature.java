package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.blockTypes.BulbSackBlock;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.List;

public class BulbSackFeature extends Feature<NoneFeatureConfiguration> {
    public BulbSackFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    private static boolean isClear(WorldGenLevel level, BlockPos pos) {
        return level.getBlockState(pos).isAir() || level.getFluidState(pos).is(FluidTags.WATER);
    }

    private static final List<Direction> DIRECTIONS = List.of(
            Direction.UP,
            Direction.DOWN,
            Direction.EAST,
            Direction.WEST,
            Direction.NORTH,
            Direction.SOUTH
    );

    private static boolean isValid(WorldGenLevel level, BlockPos pos, Direction direction) {
        return level.getBlockState(pos).isFaceSturdy(level, pos, direction.getOpposite());
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        RandomSource randomsource = context.random();

        if (!isClear(worldgenlevel, blockpos)) {
            return false;
        }

        var list = new ArrayList<>(DIRECTIONS);
        Util.shuffle(list, randomsource);

        for(Direction dir : list) {
            var offset = blockpos.offset(dir.getNormal());
            if(isValid(worldgenlevel, offset, dir)) {
                var state = FBlocks.BULBSACK.get().defaultBlockState()
                        .setValue(BulbSackBlock.FACING, dir.getOpposite())
                        .setValue(BulbSackBlock.WATERLOGGED, !worldgenlevel.getFluidState(blockpos).is(FluidTags.WATER))
                        .setValue(BulbSackBlock.SACKS, randomsource.nextIntBetweenInclusive(1, 3));

                worldgenlevel.setBlock(blockpos, state, Block.UPDATE_CLIENTS);

                return true;
            }

        }

        return false;
    }
}
