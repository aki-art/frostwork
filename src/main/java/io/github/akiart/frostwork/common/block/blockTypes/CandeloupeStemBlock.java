package io.github.akiart.frostwork.common.block.blockTypes;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.akiart.frostwork.common.FTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

/** @see net.minecraft.world.level.block.StemBlock */
public class CandeloupeStemBlock extends BushBlock implements BonemealableBlock {
    private static final int MINIMUM_LIGHT_LEVEL = 4;

    public static final MapCodec<CandeloupeStemBlock> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                        ResourceKey.codec(Registries.BLOCK).fieldOf("fruit").forGetter(stem -> stem.fruitKey),
                        ResourceKey.codec(Registries.BLOCK).fieldOf("attached_stem").forGetter(stem -> stem.attachedStemKey),
                        ResourceKey.codec(Registries.ITEM).fieldOf("seed").forGetter(stem -> stem.seedKey),
                        propertiesCodec())
                    .apply(builder, CandeloupeStemBlock::new)
    );

    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    protected static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
            Block.box(7.0, 0.0, 7.0, 9.0, 2.0, 9.0),
            Block.box(7.0, 0.0, 7.0, 9.0, 4.0, 9.0),
            Block.box(7.0, 0.0, 7.0, 9.0, 6.0, 9.0),
            Block.box(7.0, 0.0, 7.0, 9.0, 8.0, 9.0),
            Block.box(7.0, 0.0, 7.0, 9.0, 10.0, 9.0),
            Block.box(7.0, 0.0, 7.0, 9.0, 12.0, 9.0),
            Block.box(7.0, 0.0, 7.0, 9.0, 14.0, 9.0),
            Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
    };

    private final ResourceKey<Block> fruitKey;
    private final ResourceKey<Block> attachedStemKey;
    private final ResourceKey<Item> seedKey;

    public static final int MAX_AGE = 7;

    @Override
    public MapCodec<CandeloupeStemBlock> codec() {
        return CODEC;
    }

    public CandeloupeStemBlock(ResourceKey<Block> fruitKey, ResourceKey<Block> attachedStemKey, ResourceKey<Item> seedKey, Properties properties) {
        super(properties);
        this.fruitKey = fruitKey;
        this.attachedStemKey = attachedStemKey;
        this.seedKey = seedKey;

        registerDefaultState(
                this.getStateDefinition().any()
                        .setValue(AGE, 0)
        );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(AGE)];
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos blockPos) {
        return state.is(Blocks.MUD) || state.is(Blocks.FARMLAND);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        if (!level.isAreaLoaded(pos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light

        if (level.getRawBrightness(pos, 0) < MINIMUM_LIGHT_LEVEL)
            return;

        float growthSpeed = CropBlock.getGrowthSpeed(this, level, pos);
        if (!CommonHooks.onCropsGrowPre(level, pos, state, random.nextInt((int) (25.0F / growthSpeed) + 1) == 0))
            return;

        int age = state.getValue(AGE);

        if (age < MAX_AGE)
            grow(state, level, pos, age);
        else
            produceFruit(level, pos, random);

        CommonHooks.onCropsGrowPost(level, pos, state);
    }

    private void produceFruit(ServerLevel level, BlockPos pos, RandomSource random) {
        var direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        var fruitBlockPos = pos.relative(direction);
        var blockStateBelowFruit = level.getBlockState(fruitBlockPos.below());

        if (level.isEmptyBlock(fruitBlockPos) && canSustainFruit(blockStateBelowFruit)) {
            var blocks = level.registryAccess().registryOrThrow(Registries.BLOCK);
            var fruit = blocks.getOptional(this.fruitKey);
            var attachedStem = blocks.getOptional(this.attachedStemKey);

            if (fruit.isPresent() && attachedStem.isPresent()) {
                level.setBlockAndUpdate(fruitBlockPos, fruit.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction.getOpposite()));
                level.setBlockAndUpdate(pos, attachedStem.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction));
            }
        }
    }

    private static boolean canSustainFruit(BlockState state) {
        return state.is(FTags.Blocks.SUSTAINS_CANDELOUPE_FRUIT) || state.is(BlockTags.DIRT);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState blockState) {
        return new ItemStack(DataFixUtils.orElse(level.registryAccess().registryOrThrow(Registries.ITEM).getOptional(this.seedKey), this));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(AGE) != MAX_AGE;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state) {
        int currentAge = state.getValue(AGE);

        var newAge = Math.min(MAX_AGE, currentAge + Mth.nextInt(level.random, 2, 5));
        BlockState blockstate = state.setValue(AGE, newAge);
        level.setBlock(pos, blockstate, Block.UPDATE_CLIENTS);

        if (currentAge == MAX_AGE)
            blockstate.randomTick(level, pos, level.random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    private static void grow(BlockState state, ServerLevel level, BlockPos pos, int age) {
        level.setBlock(pos, state.setValue(AGE, age + 1), Block.UPDATE_CLIENTS);
    }

    // NeoForge stuff
    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.CROP;
    }
}
