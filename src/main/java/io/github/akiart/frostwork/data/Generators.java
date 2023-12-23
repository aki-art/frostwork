package io.github.akiart.frostwork.data;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.data.lang.FLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Frostwork.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Generators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGen = event.getGenerator();
        var output = dataGen.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        Frostwork.LOGGER.info("DATAGEN BEGIN");
        // Items & Blocks
        dataGen.addProvider(event.includeServer(), new FBlockStateProvider(output, Frostwork.MOD_ID, fileHelper));
        dataGen.addProvider(event.includeServer(), new FItemModelProvider(output, Frostwork.MOD_ID, fileHelper));

        // Lang
        dataGen.addProvider (event.includeServer(), new FLanguageProvider(dataGen.getPackOutput(), Frostwork.MOD_ID, "en_us"));
    }
}
