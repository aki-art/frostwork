package io.github.akiart.frostwork.data;

import com.mojang.logging.LogUtils;
import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.block.blockTypes.BulbSackBlock;
import io.github.akiart.frostwork.common.block.blockTypes.CandeloupeFruitBlock;
import io.github.akiart.frostwork.common.block.blockTypes.FoamBlock;
import io.github.akiart.frostwork.common.block.registrySets.AbstractWoodBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.MushroomBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.WoodBlockSet;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.slf4j.Logger;

public abstract class FBlockStateProviderBase extends BlockStateProvider {
    public FBlockStateProviderBase(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    private static final Logger LOGGER = LogUtils.getLogger();
    protected ResourceLocation getVanillaLocation(String name) {
        return new ResourceLocation("block/" + name);
    }

    protected void mushroomBlock(DeferredBlock<? extends HugeMushroomBlock> block, ResourceLocation insideTexture) {

        var baseModel = models()
                .withExistingParent(getBlockName(block).getPath(), "template_single_face")
                .texture("texture", blockTexture(block.get()));

        var insideModel = models()
                .withExistingParent(getBlockName(block).getPath() + "_inside", "template_single_face")
                .texture("texture", insideTexture);

        getMultipartBuilder(block.get())
                .part().modelFile(baseModel).uvLock(true).rotationX(270).addModel().condition(HugeMushroomBlock.UP, true).end()
                .part().modelFile(baseModel).uvLock(true).rotationX(90).addModel().condition(HugeMushroomBlock.DOWN, true).end()
                .part().modelFile(baseModel).uvLock(true).addModel().condition(HugeMushroomBlock.NORTH, true).end()
                .part().modelFile(baseModel).uvLock(true).rotationY(90).addModel().condition(HugeMushroomBlock.EAST, true).end()
                .part().modelFile(baseModel).uvLock(true).rotationY(180).addModel().condition(HugeMushroomBlock.SOUTH, true).end()
                .part().modelFile(baseModel).uvLock(true).rotationY(270).addModel().condition(HugeMushroomBlock.WEST, true).end()

                .part().modelFile(insideModel).uvLock(true).rotationX(270).addModel().condition(HugeMushroomBlock.UP, false).end()
                .part().modelFile(insideModel).uvLock(true).rotationX(90).addModel().condition(HugeMushroomBlock.DOWN, false).end()
                .part().modelFile(insideModel).uvLock(true).addModel().condition(HugeMushroomBlock.NORTH, false).end()
                .part().modelFile(insideModel).uvLock(true).rotationY(90).addModel().condition(HugeMushroomBlock.EAST, false).end()
                .part().modelFile(insideModel).uvLock(true).rotationY(180).addModel().condition(HugeMushroomBlock.SOUTH, false).end()
                .part().modelFile(insideModel).uvLock(true).rotationY(270).addModel().condition(HugeMushroomBlock.WEST, false).end();

        models().cubeAll(getBlockName(block).getPath() + "_inventory", blockTexture(block.get()));
    }

    protected void bulbSack(DeferredBlock<? extends BulbSackBlock> block) {

        getVariantBuilder(block.get()).forAllStates(state -> {
            var sacks = state.getValue(BulbSackBlock.SACKS);

            var modelName = switch(sacks) {
                default -> "sack_single";
                case 2 -> "sack_double";
                case 3 -> "sack_triple";
            };

            var baseModel = models()
                    .withExistingParent(getBlockName(block).getPath() + "_"  + sacks, getLocation(modelName))
                    .renderType("translucent")
                    .texture("texture", blockTexture(block.get()));

            var facing = state.getValue(BulbSackBlock.FACING);
            var onSide = facing.getAxis().isHorizontal();

            int x = onSide ? 90 : (facing == Direction.DOWN ? 180 : 0);
            int y = onSide ? ((((int)facing.toYRot()) + 180) % 360) : 0;

            return ConfiguredModel.builder()
                    .modelFile(baseModel)
                    .rotationX(x)
                    .rotationY(y)
                    .build();
        });
    }

    protected void candeloupe(DeferredBlock<? extends LanternBlock> block, ResourceLocation texture) {
        getVariantBuilder(block.get()).forAllStates(state -> {

            var hanging = state.getValue(LanternBlock.HANGING);
            var rotation = state.getValue(CandeloupeFruitBlock.FACING);
            var isAttached = state.getValue(CandeloupeFruitBlock.IS_ATTACHED);

            if(hanging) {
                String name = getBlockName(block).getPath() + "_hanging";

                ModelFile model = models()
                        .withExistingParent(name, new ResourceLocation(Frostwork.MOD_ID, "block/fruit_lantern_hanging"))
                        .texture("0", texture)
                        .renderType("cutout");

                return ConfiguredModel.builder()
                        .modelFile(model)
                        .build();
            }
            else {
                int y = (((int)rotation.toYRot()) + 90) % 360; // 90 more rotated because i copied the original stem, which is also 90 rotated


                String name = getBlockName(block).getPath();
                if(isAttached)
                    name += "_attached";

                var modelName = isAttached ? "block/fruit_lantern_attached" : "block/fruit_lantern";

                var model = models()
                        .withExistingParent(name, new ResourceLocation(Frostwork.MOD_ID, modelName))
                        .texture("0", getLocation("candeloupe"))
                        .renderType("cutout");

                if(isAttached)
                    model.texture("4", getLocation("attached_candelopue_stem"));

                return ConfiguredModel.builder()
                        .rotationY(y)
                        .modelFile(model)
                        .build();
            }
        });
    }

    protected void foam(DeferredBlock<? extends FoamBlock> block) {
        getVariantBuilder(block.get()).forAllStates(state -> {

            var stage = state.getValue(FoamBlock.FOAM_AMOUNT);
            String name = getBlockName(block).getPath() + "_" + stage;

            ModelFile model = models()
                    .singleTexture(name, getVanillaLocation("lily_pad"), getLocation(name))
                    .renderType("translucent");

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }

    protected ResourceLocation getLocation(String name) {
        return new ResourceLocation(Frostwork.MOD_ID, "block/" + name);
    }

//    private String getName(Block block) {
//        return block.
//    }

    protected void crossBlock(DeferredBlock<? extends Block> block) {
        ModelFile model = models()
                .cross(getBlockName(block).getPath(), blockTexture(block.get()))
                .renderType("cutout");

        simpleBlock(block.get(), model);
    }

    protected void mushroom(MushroomBlockSet blockSet) {

        ResourceLocation plankTexture = blockTexture(blockSet.planks.get());


        woods(blockSet, plankTexture);
        logBlock(blockSet.stem.get());
        mushroomBlock(blockSet.cap, getLocation("grimcap_stem_top"));
        logBlock(blockSet.strippedStem.get());
    }

    protected void wood(AbstractWoodBlockSet blockSet) {

        ResourceLocation plankTexture = blockTexture(blockSet.planks.get());
        woods(blockSet, plankTexture);

        if(blockSet instanceof WoodBlockSet wood) {
            ResourceLocation logTex = blockTexture(wood.log.get());
            ResourceLocation strippedLogTex = blockTexture(wood.strippedLog.get());

            simpleBlock(wood.leaves.get());

            logBlock(wood.log.get());
            logBlock(wood.strippedLog.get());
            axisBlock(wood.strippedWood.get(), strippedLogTex, strippedLogTex);
            axisBlock(wood.wood.get(), logTex, logTex);

            crossBlock(wood.sapling);
        }
    }

    protected void woods(AbstractWoodBlockSet set, ResourceLocation plankTexture) {
        simpleBlock(set.planks.get());
        slabBlock(set.slab.get(), set.planks.getId(), plankTexture);
        fenceBlock(set.fence.get(), plankTexture);
        fenceGateBlock(set.fenceGate.get(), plankTexture);
        buttonBlock(set.button.get(), plankTexture);
        pressurePlateBlock(set.pressurePlate.get(), plankTexture);
        stairsBlock(set.stairs.get(), plankTexture);
        trapdoorBlockWithRenderType(
                set.trapDoor.get(),
                getLocation(set.getName() + "_trapdoor"),
                true,
                set.doorRenderType);

        doorBlockWithRenderType(
                set.door.get(),
                getLocation(set.getName() + "_door_bottom"),
                getLocation(set.getName() + "_door_top"),
                set.doorRenderType);
    }

    protected ResourceLocation getBlockName(DeferredBlock<?> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get());
    }

    protected void tallPlant(DeferredBlock<? extends Block> block) {
        String name = getBlockName(block).getPath();

        getVariantBuilder(block.get()).forAllStates(state -> {
            DoubleBlockHalf half = state.getValue(DoublePlantBlock.HALF);
            String partName = name + "_" + half.getSerializedName();

            ModelFile model = models()
                    .cross(partName, getLocation(partName))
                    .renderType("cutout");

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    protected void translucentSimpleBlock(DeferredBlock<?> block) {
        var model = models()
                .cubeAll(getBlockName(block).getPath(), blockTexture(block.get()))
                .renderType("translucent");

        var models = ConfiguredModel.builder().modelFile(model).build();
        simpleBlock(block.get(), models);
    }

    protected void stem(DeferredBlock<? extends Block> unattachedStem, DeferredBlock<? extends Block> attachedStem) {

        getVariantBuilder(unattachedStem.get()).forAllStates(state -> {
            var age = state.getValue(StemBlock.AGE);
            var modelLocation = getLocation(String.format("gourd_stem_%d", age));
            var name = String.format("candeloupe_stem_stage_%d", age);

            var model = models()
                    .withExistingParent(name, modelLocation)
                    .renderType("cutout")
                    .texture("1", getLocation("candeloupe_stem"))
                    .texture("2", getLocation("candeloupe_leaf"));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });

        var modelLocation = getLocation("attached_gourd_stem");

        getVariantBuilder(attachedStem.get()).forAllStates(state -> {
            var rotation = state.getValue(AttachedStemBlock.FACING);

            int y = (((int)rotation.toYRot()) + 270) % 360; // 90 more rotated because i copied the original model, which is also 90 rotated

            var model = models()
                    .withExistingParent(getBlockName(attachedStem).getPath(), modelLocation)
                    .renderType("cutout")
                    .texture("1", getLocation("candeloupe_stem"))
                    .texture("2", getLocation("candeloupe_leaf"));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .rotationY(y)
                    .build();
        });
    }

    protected void snowyBlock(SnowyDirtBlock block) {
        getVariantBuilder(block).forAllStates(state -> {

            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);

            ResourceLocation all = blockTexture(block);
            ModelFile model;
            var name = BuiltInRegistries.BLOCK.getKey(block).getPath();

            if (snowy) {
                ResourceLocation side = getLocation("snowy_" + name);
                ResourceLocation top = new ResourceLocation("block/snow");
                model = models().cubeBottomTop(name, side, all, top);
            } else {
                model = models().cubeAll("snowy_" + name, all);
            }

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    protected void stones(StoneBlockSet blockSet) {
        ResourceLocation texture = blockTexture(blockSet.block.get());

        if(blockSet.equals(FBlocks.POLISHED_AQUAMIRE)) {
            Block block = blockSet.block.get();
            getVariantBuilder(block).forAllStates(state -> {

                ResourceLocation all = blockTexture(block);
                ModelFile model;
                var name = BuiltInRegistries.BLOCK.getKey(block).getPath();

                ResourceLocation side = getLocation(name +"_side");

                model = models().cubeColumn(name, side, all);

                return ConfiguredModel.builder().modelFile(model).build();
            });
        }
        else {
            simpleBlock(blockSet.block.get());
        }
        stairsBlock(blockSet.stairs.get(), texture);
        wallBlock(blockSet.wall.get(), texture);
        slabBlock(blockSet.slab.get(), blockSet.block.getId(), texture);

        if (blockSet.hasRedStoneComponents()) {
            buttonBlock(blockSet.button.get(), texture);
            pressurePlateBlock(blockSet.pressurePlate.get(), texture);
        }
    }
}
