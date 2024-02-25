package io.github.akiart.frostwork.utils;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class TemplateUtil {

    private static final int BARRIER_FLAGS = Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_INVISIBLE;

    public static BoundingBox getChunkBox(ChunkPos chunkPos, BlockGetter level) {
        return new BoundingBox(
                chunkPos.getMinBlockX() - 16,
                level.getMinBuildHeight(),
                chunkPos.getMinBlockZ() - 16,
                chunkPos.getMaxBlockX() + 16,
                level.getMaxBuildHeight(),
                chunkPos.getMaxBlockZ() + 16
        );
    }

    private static final Direction[] directions = new Direction[] {
            Direction.UP,
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST
    };

    // ignores entities, does not replace existing blocks
    public static boolean placeInWorld(
            StructureTemplate template, ServerLevelAccessor level, BlockPos offset, BlockPos pos, StructurePlaceSettings settings, RandomSource random, Predicate<BlockState> replaceable, int flags
    ) {
        if (template.palettes.isEmpty())
            return false;

        List<StructureTemplate.StructureBlockInfo> randomizedPalette = settings.getRandomPalette(template.palettes, offset).blocks();

        if (randomizedPalette.isEmpty()
                || template.getSize().getX() < 1
                || template.getSize().getY() < 1
                || template.getSize().getZ() < 1) {
            return false;
        }

        BoundingBox boundingbox = settings.getBoundingBox();
        List<BlockPos> fluidContainers = Lists.newArrayListWithCapacity(settings.shouldKeepLiquids() ? randomizedPalette.size() : 0);
        List<BlockPos> fluidSourceBlocks = Lists.newArrayListWithCapacity(settings.shouldKeepLiquids() ? randomizedPalette.size() : 0);
        List<Pair<BlockPos, CompoundTag>> placedBlocks = Lists.newArrayListWithCapacity(randomizedPalette.size());

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;

        List<StructureTemplate.StructureBlockInfo> structureBlockInfos = StructureTemplate.processBlockInfos(
                level, offset, pos, settings, randomizedPalette, template
        );

        for(StructureTemplate.StructureBlockInfo info : structureBlockInfos) {

            BlockPos blockpos = info.pos();
            if (boundingbox == null || boundingbox.isInside(blockpos)) {

                // check for replaceability
                BlockState existingBlock = level.getBlockState(blockpos);
                if(!replaceable.test(existingBlock))
                    continue;

                FluidState fluidstate = settings.shouldKeepLiquids() ? level.getFluidState(blockpos) : null;
                BlockState blockstate = info.state().mirror(settings.getMirror()).rotate(level, blockpos, settings.getRotation());

                clearBlock(level, info, blockpos);

                if (level.setBlock(blockpos, blockstate, flags)) {
                    minX = Math.min(minX, blockpos.getX());
                    minY = Math.min(minY, blockpos.getY());
                    minZ = Math.min(minZ, blockpos.getZ());
                    maxX = Math.max(maxX, blockpos.getX());
                    maxY = Math.max(maxY, blockpos.getY());
                    maxZ = Math.max(maxZ, blockpos.getZ());

                    placedBlocks.add(Pair.of(blockpos, info.nbt()));
                    setContainerSeed(level, random, info, blockpos);
                    placeLiquids(level, fluidstate, blockstate, fluidSourceBlocks, blockpos, fluidContainers);
                }
            }
        }

        boolean flag = true;

        while(flag && !fluidContainers.isEmpty()) {
            flag = false;
            Iterator<BlockPos> iterator = fluidContainers.iterator();

            while(iterator.hasNext()) {
                BlockPos blockpos3 = iterator.next();
                FluidState fluidstate2 = level.getFluidState(blockpos3);

                for(int i2 = 0; i2 < directions.length && !fluidstate2.isSource(); ++i2) {
                    BlockPos blockpos1 = blockpos3.relative(directions[i2]);
                    FluidState fluidstate1 = level.getFluidState(blockpos1);
                    if (fluidstate1.isSource() && !fluidSourceBlocks.contains(blockpos1)) {
                        fluidstate2 = fluidstate1;
                    }
                }

                if (fluidstate2.isSource()) {
                    BlockState blockstate1 = level.getBlockState(blockpos3);
                    Block block = blockstate1.getBlock();
                    if (block instanceof LiquidBlockContainer) {
                        ((LiquidBlockContainer)block).placeLiquid(level, blockpos3, blockstate1, fluidstate2);
                        flag = true;
                        iterator.remove();
                    }
                }
            }
        }

        if (minX <= maxX) {
            if (!settings.getKnownShape()) {
                DiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(maxX - minX + 1, maxY - minY + 1, maxZ - minZ + 1);

                for(Pair<BlockPos, CompoundTag> pair1 : placedBlocks) {
                    BlockPos blockpos2 = pair1.getFirst();
                    discretevoxelshape.fill(blockpos2.getX() - minX, blockpos2.getY() - minY, blockpos2.getZ() - minZ);
                }

                StructureTemplate.updateShapeAtEdge(level, flags, discretevoxelshape, minX, minY, minZ);
            }

            for(Pair<BlockPos, CompoundTag> pair : placedBlocks) {
                BlockPos blockpos4 = pair.getFirst();
                if (!settings.getKnownShape()) {
                    BlockState blockstate2 = level.getBlockState(blockpos4);
                    BlockState blockstate3 = Block.updateFromNeighbourShapes(blockstate2, level, blockpos4);
                    if (blockstate2 != blockstate3) {
                        level.setBlock(blockpos4, blockstate3, flags & -2 | 16);
                    }

                    level.blockUpdated(blockpos4, blockstate3.getBlock());
                }

                if (pair.getSecond() != null) {
                    BlockEntity blockentity2 = level.getBlockEntity(blockpos4);
                    if (blockentity2 != null) {
                        blockentity2.setChanged();
                    }
                }
            }
        }

        return true;

    }

    private static void placeLiquids(ServerLevelAccessor level, FluidState fluidstate, BlockState blockstate, List<BlockPos> fluidSourceBlocks, BlockPos blockpos, List<BlockPos> fluidContainers) {
        if (fluidstate != null) {
            if (blockstate.getFluidState().isSource()) {
                fluidSourceBlocks.add(blockpos);

            } else if (blockstate.getBlock() instanceof LiquidBlockContainer) {
                ((LiquidBlockContainer) blockstate.getBlock()).placeLiquid(level, blockpos, blockstate, fluidstate);

                if (!fluidstate.isSource())
                    fluidContainers.add(blockpos);
            }
        }
    }

    private static void clearBlock(ServerLevelAccessor level, StructureTemplate.StructureBlockInfo info, BlockPos blockpos) {
        if (info.nbt() != null) {
            BlockEntity blockentity = level.getBlockEntity(blockpos);
            Clearable.tryClear(blockentity);
            level.setBlock(blockpos, Blocks.BARRIER.defaultBlockState(), BARRIER_FLAGS);
        }
    }

    private static void setContainerSeed(ServerLevelAccessor level, RandomSource random, StructureTemplate.StructureBlockInfo info, BlockPos blockpos) {
        if (info.nbt() != null) {
            BlockEntity blockentity1 = level.getBlockEntity(blockpos);
            if (blockentity1 != null) {
                if (blockentity1 instanceof RandomizableContainer) {
                    info.nbt().putLong("LootTableSeed", random.nextLong());
                }

                blockentity1.load(info.nbt());
            }
        }
    }

}
