package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.init.item.registrySets.StoneItemSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

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
