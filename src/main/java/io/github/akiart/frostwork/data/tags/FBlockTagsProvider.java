package io.github.akiart.frostwork.data.tags;

import io.github.akiart.frostwork.common.FTags;
import io.github.akiart.frostwork.common.init.FBlocks;
import io.github.akiart.frostwork.common.init.block.BlockRegistryUtil;
import io.github.akiart.frostwork.common.init.block.registrySets.AbstractWoodBlockSet;
import io.github.akiart.frostwork.common.init.block.registrySets.StoneBlockSet;
import io.github.akiart.frostwork.common.init.block.registrySets.WoodBlockSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FBlockTagsProvider extends BlockTagsProvider {

    public FBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        BlockRegistryUtil.getStones().forEach(this::stone);
        BlockRegistryUtil.getMushrooms().forEach(this::tree);
        BlockRegistryUtil.getWoods().forEach(this::tree);

        tag(FTags.Blocks.EDELSTONE_REPLACEABLE)
                .add(FBlocks.EDELSTONE.block.get());

        tag(BlockTags.BEACON_BASE_BLOCKS).add(
                FBlocks.WOLF_BLOCK.get(),
                FBlocks.MALACHITE_BLOCK.get()
        );

        tag(BlockTags.COAL_ORES).add(FBlocks.EDELSTONE_COAL_ORE.get());
        tag(Tags.Blocks.ORES_COAL).add(FBlocks.EDELSTONE_COAL_ORE.get());
        tag(Tags.Blocks.ORES).add(FBlocks.EDELSTONE_COAL_ORE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                FBlocks.EDELSTONE_COAL_ORE.get(),
                FBlocks.WOLF_BLOCK.get(),
                FBlocks.MALACHITE_BLOCK.get()
        );

        tag(Tags.Blocks.NEEDS_WOOD_TOOL).add(
                FBlocks.WOLF_BLOCK.get(),
                FBlocks.MALACHITE_BLOCK.get()
        );
    }

    private void tree(AbstractWoodBlockSet set) {
        tag(Tags.Blocks.FENCES).add(set.fence.get());
        tag(Tags.Blocks.FENCE_GATES_WOODEN).add(set.fenceGate.get());
        tag(Tags.Blocks.FENCES_WOODEN).add(set.fence.get());

        tag(BlockTags.WOODEN_DOORS).add(set.door.get());
        tag(BlockTags.DOORS).add(set.door.get());
        tag(BlockTags.WOODEN_STAIRS).add(set.stairs.get());
        tag(BlockTags.STAIRS).add(set.stairs.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(set.trapDoor.get());
        tag(BlockTags.TRAPDOORS).add(set.trapDoor.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(set.pressurePlate.get());
        tag(BlockTags.PRESSURE_PLATES).add(set.pressurePlate.get());
        tag(BlockTags.WOODEN_BUTTONS).add(set.button.get());
        tag(BlockTags.BUTTONS).add(set.button.get());
        tag(BlockTags.WOODEN_SLABS).add(set.slab.get());
        tag(BlockTags.SLABS).add(set.slab.get());
        tag(Tags.Blocks.FENCES_WOODEN).add(set.fence.get());
        tag(BlockTags.WOODEN_FENCES).add(set.fence.get());
        tag(BlockTags.FENCES).add(set.fence.get());
        tag(BlockTags.FENCE_GATES).add(set.fenceGate.get());

        tag(BlockTags.MINEABLE_WITH_AXE).add(
                set.planks.get(),
                set.fence.get(),
                set.trapDoor.get(),
                set.fenceGate.get(),
                set.stairs.get(),
                set.slab.get(),
                set.button.get(),
                set.pressurePlate.get()
        );

        if(set instanceof WoodBlockSet wood) {
            tag(BlockTags.LOGS).add(
                    wood.log.get(),
                    wood.strippedWood.get(),
                    wood.wood.get(),
                    wood.strippedWood.get()
            );

            tag(BlockTags.MINEABLE_WITH_AXE).add(
                    wood.log.get(),
                    wood.strippedWood.get(),
                    wood.wood.get(),
                    wood.strippedWood.get()
            );
        }
    }

    private void stone(StoneBlockSet set) {
        tag(Tags.Blocks.STONE).add(set.block.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(set.block.get())
                .add(set.stairs.get())
                .add(set.slab.get())
                .add(set.wall.get());

        tag(Tags.Blocks.NEEDS_WOOD_TOOL)
                .add(set.block.get())
                .add(set.stairs.get())
                .add(set.slab.get())
                .add(set.wall.get());

        if(set.hasRedStoneComponents()) {
            tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(set.pressurePlate.get())
                    .add(set.button.get());

            tag(Tags.Blocks.NEEDS_WOOD_TOOL)
                    .add(set.pressurePlate.get())
                    .add(set.button.get());
        }

        tag(BlockTags.WALLS).add(set.wall.get());
    }
}
