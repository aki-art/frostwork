package io.github.akiart.frostwork.common.init;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.init.item.ItemRegistryUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Frostwork.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> FROSTWORK = CREATIVE_MODE_TABS.register("frostwork_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> FItems.OBSIDIAN_BRICKS.block.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ItemRegistryUtil.all.forEach(item -> output.accept(item.get()));
            }).build());
}
