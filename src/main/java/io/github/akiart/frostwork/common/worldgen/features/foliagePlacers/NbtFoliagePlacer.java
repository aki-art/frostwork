package io.github.akiart.frostwork.common.worldgen.features.foliagePlacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.common.worldgen.features.FFoliagePlayerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NbtFoliagePlacer extends FoliagePlacer {
    private static final int BLOCK_UPDATE_FLAGS = Block.UPDATE_KNOWN_SHAPE
            | Block.UPDATE_CLIENTS
            | Block.UPDATE_NEIGHBORS;
    private final List<FoliageNbt> foliageOptions;

    public static Codec<NbtFoliagePlacer> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    FoliageNbt.CODEC.listOf().fieldOf("foliage_options").forGetter(placer -> placer.foliageOptions),
                    IntProvider.CODEC.fieldOf("offset").forGetter(placer -> placer.offset)
            ).apply(instance, NbtFoliagePlacer::new)
    );

    public record FoliageNbt(ResourceLocation structure, Vec3i offset) {
        public static Codec<NbtFoliagePlacer.FoliageNbt> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        ResourceLocation.CODEC.fieldOf("structure").forGetter(FoliageNbt::structure),
                        Vec3i.CODEC.fieldOf("offset").forGetter(FoliageNbt::offset)
                ).apply(instance, FoliageNbt::new));
    }

    public NbtFoliagePlacer(List<FoliageNbt> foliageOptions, IntProvider offset) {
        super(ConstantInt.of(0), offset);
        this.foliageOptions = foliageOptions;
    }

    @Override
    @NotNull
    protected FoliagePlacerType<?> type() {
        return FFoliagePlayerTypes.NBT_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter blockSetter, RandomSource random, TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageOption, int foliageRadius, int offset) {

        FoliageNbt foliagePreset = foliageOptions.get(foliageOption);
        Vec3i pivot = foliagePreset.offset();

        var level = (WorldGenLevel)levelSimulatedReader;
        var blockPos = attachment.pos();

        StructureTemplateManager templateManager = level.getLevel().getServer().getStructureManager();
        StructureTemplate template = templateManager.getOrCreate(foliagePreset.structure());

        ChunkPos chunkPos = new ChunkPos(blockPos);
        Rotation rotation = Rotation.getRandom(random);


        BoundingBox boundingBox = new BoundingBox(
                chunkPos.getMinBlockX() - 16,
                level.getMinBuildHeight(),
                chunkPos.getMinBlockZ() - 16,
                chunkPos.getMaxBlockX() + 16,
                level.getMaxBuildHeight(),
                chunkPos.getMaxBlockZ() + 16
        );

        BlockPos centerPos = blockPos.offset(-pivot.getX(), 0, -pivot.getZ());

        StructurePlaceSettings placeSettings = new StructurePlaceSettings()
                .setRotation(rotation)
                .setRotationPivot(new BlockPos(pivot))
                .setBoundingBox(boundingBox)
                .setRandom(random);

        template.placeInWorld(level, centerPos, centerPos, placeSettings, random, BLOCK_UPDATE_FLAGS);
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return random.nextInt(foliageOptions.size() -1 );
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return false;
    }
}
