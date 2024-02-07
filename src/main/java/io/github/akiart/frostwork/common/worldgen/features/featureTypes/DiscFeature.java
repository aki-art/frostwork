package io.github.akiart.frostwork.common.worldgen.features.featureTypes;

import com.mojang.serialization.Codec;
import io.github.akiart.frostwork.common.worldgen.features.configTypes.DiscConfig;
import io.github.akiart.frostwork.utils.VoxelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.block.Block;

public class DiscFeature extends Feature<DiscConfig> {
    public DiscFeature(Codec<DiscConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<DiscConfig> context) {

        var random = context.random();
        var radius = context.config().radius().sample(random);
        var block = context.config().block();
        var offsets = VoxelUtil.getCircle(radius, context.origin());
        var blockPos = new BlockPos.MutableBlockPos();

        for(var offset : offsets) {
            blockPos.set(offset);
            context.level().setBlock(blockPos, block.getState(random, blockPos), Block.UPDATE_CLIENTS);
        }

        return true;
    }
}
