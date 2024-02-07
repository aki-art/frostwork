package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.data.lang.FLanguageProvider;
import io.github.akiart.frostwork.data.tags.FBIomeTagsProvider;
import io.github.akiart.frostwork.data.tags.FBlockTagsProvider;
import io.github.akiart.frostwork.data.tags.FItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Frostwork.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Generators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGen = event.getGenerator();
        PackOutput output = dataGen.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        FBlockTagsProvider blockTagsProvider = new FBlockTagsProvider(output, lookupProvider, Frostwork.MOD_ID, fileHelper);

        dataGen.addProvider(event.includeServer(), new FBlockStateProvider(output, Frostwork.MOD_ID, fileHelper));
        dataGen.addProvider(event.includeServer(), new FItemModelProvider(output, Frostwork.MOD_ID, fileHelper));
        dataGen.addProvider(event.includeServer(), new FWorldGenProvider(output, lookupProvider));
        dataGen.addProvider(event.includeServer(), new LootTables(output));
        dataGen.addProvider(event.includeServer(), blockTagsProvider);
        dataGen.addProvider(event.includeServer(), new FItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter()));
        dataGen.addProvider(event.includeServer(), new FBIomeTagsProvider(output, lookupProvider, Frostwork.MOD_ID, fileHelper));
        dataGen.addProvider(event.includeServer(), new FRecipeProvider(output, lookupProvider));

        dataGen.addProvider (event.includeServer(), new FLanguageProvider(dataGen.getPackOutput(), Frostwork.MOD_ID, "en_us"));

        dataGen.addProvider(event.includeServer(), new FCuriosDataProvider(Frostwork.MOD_ID, output, fileHelper, lookupProvider));
    }
}
