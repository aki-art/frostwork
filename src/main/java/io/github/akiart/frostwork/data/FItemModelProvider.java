package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.common.init.item.ItemRegistryUtil;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FItemModelProvider extends FItemModelProviderBase {
    public FItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemRegistryUtil.stones.forEach(this::stone);
    }
}
