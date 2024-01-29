package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.registrySets.AbstractWoodBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.MushroomBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.block.registrySets.WoodBlockSet;
import io.github.akiart.frostwork.common.item.registrySets.AbstractWoodItemSet;
import io.github.akiart.frostwork.common.item.registrySets.StoneItemSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public abstract class FItemModelProviderBase extends ItemModelProvider {
    public FItemModelProviderBase(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    protected ResourceLocation getBlockTexture(Block block) {
        ResourceLocation name = BuiltInRegistries.BLOCK.getKey(block);
        return new ResourceLocation(name.getNamespace(), "block/" + name.getPath());
    }

    protected ResourceLocation getItemTexture(Item item) {
        ResourceLocation name = BuiltInRegistries.ITEM.getKey(item);
        return new ResourceLocation(name.getNamespace(), "item/" + name.getPath());
    }

    protected ResourceLocation getBlockLocation(String name) {
        return new ResourceLocation(Frostwork.MOD_ID, "block/" + name);
    }

    protected ResourceLocation getItemLocation(String name) {
        return new ResourceLocation(Frostwork.MOD_ID, "item/" + name);
    }

    protected <BlockType extends Block> void fromBlock(DeferredBlock<BlockType> block) {
        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(block.get());
        var path = key.getPath();
        withExistingParent(path, getBlockLocation(path));
    }

    protected ResourceLocation getBlockName(DeferredBlock<?> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get());
    }

    protected void wood(AbstractWoodItemSet set) {

        Frostwork.LOGGER.info("wood");

        AbstractWoodBlockSet blocks = set.getTree();

        ResourceLocation plankTex = getBlockTexture(blocks.planks.get());

        fromBlock(blocks.planks);
        buttonInventory(getBlockName(blocks.button).getPath(), plankTex);
        pressurePlate(getBlockName(blocks.pressurePlate).getPath(), plankTex);

        miscItem(set.door);

        fromBlock(blocks.stairs);
        stairs(set.stairs.getId().getPath(), plankTex, plankTex, plankTex);
        fenceInventory(getBlockName(blocks.fence).getPath(), plankTex);
        fenceGate(getBlockName(blocks.fenceGate).getPath(), plankTex);
        slab(set.slab.getId().getPath(), plankTex, plankTex, plankTex);
        trapdoorBottom(blocks.trapDoor.getId().getPath(), getBlockLocation(set.trapDoor.getId().getPath()));

        if(blocks instanceof WoodBlockSet wood) {
            fromBlock(wood.leaves);
            fromBlock(wood.log);
            fromBlock(wood.strippedWood);
            fromBlock(wood.wood);
            fromBlock(wood.strippedLog);
            saplingItem(wood.sapling);
        }
        else if(blocks instanceof MushroomBlockSet mushroom) {
            fromBlock(mushroom.cap);

            var inventoryName = getBlockName(mushroom.cap).getPath() + "_inventory";
            withExistingParent(getBlockName(mushroom.cap).getPath(), getBlockLocation(inventoryName));

            fromBlock(mushroom.stem);
            fromBlock(mushroom.strippedStem);
        }
    }

    private ItemModelBuilder saplingItem(DeferredBlock<? extends Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Frostwork.MOD_ID,"block/" + item.getId().getPath()));
    }

    public void tallPlant(DeferredBlock<? extends Block> plant) {
        generate(plant.getId().getPath(), getBlockLocation(getBlockName(plant).getPath() + "_upper"));
    }

    public void miscItem(DeferredItem<? extends Item> item) {
        var name = item.getId().getPath();

        if (item.get() instanceof SpawnEggItem) {
            //withExistingParent(name, new ResourceLocation("item/template_spawn_egg"));
        } else {
            generate(name, getItemTexture(item.get()));
        }
    }

    protected ItemModelBuilder generate(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    protected void stone(StoneItemSet items) {
        StoneBlockSet blocks = items.getStoneRegistryObject();
        ResourceLocation texture = getBlockTexture(blocks.block.get());

        fromBlock(blocks.block);
        stairs(items.stairs.getId().getPath(), texture, texture, texture);
        wallInventory(items.wall.getId().getPath(), texture);
        slab(items.slab.getId().getPath(), texture, texture, texture);

        if (items.hadRedstoneComponents()) {
            buttonInventory(blocks.button.getId().getPath(), texture);
            fromBlock(blocks.pressurePlate);
        }
    }
}
