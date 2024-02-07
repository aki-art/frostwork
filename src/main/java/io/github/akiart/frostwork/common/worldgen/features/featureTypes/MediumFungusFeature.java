package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.MediumFungusFeatureConfig;
import io.github.akiart.frostwork.utils.VoxelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.HashSet;
import java.util.Set;

public class MediumFungusFeature extends Feature<MediumFungusFeatureConfig> {
    public MediumFungusFeature(Codec<MediumFungusFeatureConfig> pCodec) {
        super(pCodec);
    }

    private static final Direction[] STEM_DECORATION_DIRECTIONS = new Direction[] {
            Direction.SOUTH,
            Direction.EAST,
            Direction.NORTH,
            Direction.WEST
    };

    @Override
    public boolean place(FeaturePlaceContext<MediumFungusFeatureConfig> context) {
        var random = context.random();
        var level = context.level();

        var stemLength = context.config().stemLength().sample(random);
        var blockPos = context.origin().mutable();
        var blockPos2 = context.origin().mutable();
        Vec3i origin = context.origin().above(stemLength);
        Set<BlockPos> blocks = new HashSet<>();

        for(int y = 0; y < stemLength; y++) {
            level.setBlock(blockPos, context.config().stem().getState(random, blockPos), Block.UPDATE_CLIENTS);

            blocks.add(blockPos.immutable());

            blockPos.move(Direction.UP);
        }

        var radius = context.config().capRadius().sample(random);
        var isSquare = random.nextFloat() < context.config().squareChance();

        if(radius == 0) {
            level.setBlock(blockPos, context.config().cap().getState(random, blockPos), Block.UPDATE_CLIENTS);
            return true;
        }

        if(isSquare)
            placeSquareCap(context, radius, blockPos, origin, level, random, blocks);
        else
            placeRoundCap(context, radius, blockPos, origin, level, random, blocks);

        for(var pos : blocks) {
            for(var dir : Direction.values()) {
                var offset = pos.relative(dir);
                var state = level.getBlockState(offset);

                if(state.isAir() && random.nextFloat() < context.config().decorationChance()) {
                    context.config().decoration().value().place(level, context.chunkGenerator(), random, offset);
                }
            }
        }

        return true;
    }

    private static void placeRoundCap(FeaturePlaceContext<MediumFungusFeatureConfig> context, int radius, BlockPos.MutableBlockPos blockPos, Vec3i origin, WorldGenLevel level, RandomSource random, Set<BlockPos> blocks) {
        for(var offset : VoxelUtil.getFilledCircle(radius)) {
            blockPos.setWithOffset(origin, offset);
            level.setBlock(blockPos, context.config().cap().getState(random, blockPos), Block.UPDATE_CLIENTS);
            blocks.add(blockPos.immutable());
        }
    }

    private static void placeSquareCap(FeaturePlaceContext<MediumFungusFeatureConfig> context, int radius, BlockPos.MutableBlockPos blockPos, Vec3i origin, WorldGenLevel level, RandomSource random, Set<BlockPos> blocks) {
        for(int x = -radius; x <= radius; x++) {
            for(int z = -radius; z <= radius; z++){
                blockPos.setWithOffset(origin, x, 0, z);
                level.setBlock(blockPos, context.config().cap().getState(random, blockPos), Block.UPDATE_CLIENTS);
                blocks.add(blockPos.immutable());
            }
        }
    }
}
