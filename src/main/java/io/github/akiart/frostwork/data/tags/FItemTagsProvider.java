package io.github.akiart.frostwork.data.tags;

import io.github.akiart.frostwork.common.FTags;
import io.github.akiart.frostwork.common.item.FItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class FItemTagsProvider extends ItemTagsProvider {
    public FItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags) {
        super(pOutput, pLookupProvider, pBlockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        tag(ItemTags.ARROWS).add(FItems.TATZELWURM_ARROW.get());

        tag(FTags.Items.HUNTER_PELT).add(
                FItems.HUNTER_PELT_BROWN.get(),
                FItems.HUNTER_PELT_CREAM.get()
        );
    }

}
