package io.github.akiart.frostwork.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class FCuriosDataProvider extends CuriosDataProvider {
    public FCuriosDataProvider(String modId, PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(modId, output, fileHelper, registries);
    }
    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {

        createEntities("backpack_wearers")
                .addPlayer()
                .addSlots("back");
    }
}
