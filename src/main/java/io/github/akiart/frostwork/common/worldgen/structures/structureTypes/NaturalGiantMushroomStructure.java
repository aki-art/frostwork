//package io.github.akiart.frostwork.common.worldgen.structures.structureTypes;
//
//import com.mojang.serialization.Codec;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.ChunkPos;
//import net.minecraft.world.level.levelgen.structure.Structure;
//import net.minecraft.world.level.levelgen.structure.StructureType;
//
//import java.util.Optional;
//
//public class NaturalGiantMushroomStructure extends Structure {
//    public static final Codec<NaturalGiantMushroomStructure> CODEC = simpleCodec(NaturalGiantMushroomStructure::new);
//
//    protected NaturalGiantMushroomStructure(StructureSettings pSettings) {
//        super(pSettings);
//    }
//
//    @Override
//    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
//        ChunkPos chunkpos = context.chunkPos();
//        int x = chunkpos.getMiddleBlockX();
//        int z = chunkpos.getMiddleBlockZ();
//        var random = context.random();
//        int attempts = 64;
//        var blockPos = new BlockPos.MutableBlockPos(x, 0, z);
//
//        while(attempts-- > 0) {
//            blockPos.setY(random.nextInt(220));
//            var biome = context.biomeSource().getNoiseBiome(blockPos.getX() / 4, blockPos.getY() / 4, blockPos.getZ() / 4, context.randomState().sampler());
//
//            if(!context.validBiome().test(biome))
//                continue;
//
//
//        }
//
//        for(int y = )
//        int k = context.chunkGenerator().getFirstOccupiedHeight(x, z, pHeightmapTypes, context.heightAccessor(), context.randomState());
//        return Optional.of(new Structure.GenerationStub(new BlockPos(x, k, z), pGenerator));
//    }
//
//    @Override
//    public StructureType<?> type() {
//        return null;
//    }
//}
