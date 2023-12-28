//package io.github.akiart.frostwork.common.block.blockTypes;
//
//import com.mojang.serialization.MapCodec;
//import net.minecraft.core.BlockPos;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.LevelReader;
//import net.minecraft.world.level.block.BonemealableBlock;
//import net.minecraft.world.level.block.BushBlock;
//import net.minecraft.world.level.block.state.BlockBehaviour;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.state.properties.BlockStateProperties;
//import net.minecraft.world.level.block.state.properties.IntegerProperty;
//import org.jetbrains.annotations.NotNull;
//
//public class SnowBerryBushBottomBlock  extends BushBlock implements BonemealableBlock {
//    public static final MapCodec<SnowBerryBushBottomBlock> CODEC = simpleCodec(SnowBerryBushBottomBlock::new);
//    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
//    public static final int MAX_AGE = 3;
//    private static final int TOP_APPEAR_AGE = 1;
//    private static final int MIN_LIGHT_LEVEL = 9;
//    private static final int NATURAL_GROWTH_CHANCE = 5;
//
//    public SnowBerryBushBottomBlock(Properties properties) {
//        super(properties);
//    }
//
//    @Override
//    public BlockBehaviour.OffsetType getOffsetType() {
//        return BlockBehaviour.OffsetType.XZ;
//    }
//
//    @Override
//    protected @NotNull MapCodec<? extends BushBlock> codec() {
//        return CODEC;
//    }
//
//    @Override
//    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
//        return blockState.getValue(AGE) < 3;
//    }
//
//    @Override
//    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
//        return true;
//    }
//
//    @Override
//    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
//
//    }
//
//    public void ageUp(ServerLevel world, BlockPos pos, BlockState state) {
//        if(state.is(this)) {
//            int newAge = Math.min(MAX_AGE, state.getValue(AGE) + 1);
//            setAge(world, state, pos, newAge);
//        }
//    }
//
//    private void setAge(ServerLevel world, BlockState state, BlockPos pos, int age) {
//
//        BlockState topPiece = world.getBlockState(pos.above());
//        if (topPiece.is(FBlocks.SNOWBERRY_BUSH_TOP.get())) {
//            if(age >= TOP_APPEAR_AGE ) {
//                world.setBlock(pos.above(), topPiece.setValue(SnowBerryBushTopBlock.AGE, age), Constants.BlockFlags.BLOCK_UPDATE);
//            }
//            else {
//                world.destroyBlock(pos.above(), false);
//            }
//        }
//        else if(age >= TOP_APPEAR_AGE) {
//            placeTopPiece(world, pos, age);
//        }
//
//        world.setBlock(pos, state.setValue(AGE, age), Constants.BlockFlags.BLOCK_UPDATE);
//    }
//}
