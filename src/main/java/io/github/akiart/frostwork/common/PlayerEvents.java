package io.github.akiart.frostwork.common;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.block.FBlocks;
import io.github.akiart.frostwork.common.item.FItems;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@Mod.EventBusSubscriber(modid = Frostwork.MOD_ID)
public class PlayerEvents {

    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event)
    {
        UseOnContext context = event.getUseOnContext();
        Level level = context.getLevel();
        ItemStack stack = context.getItemInHand();
        Item item = stack.getItem();
        if (item instanceof BottleItem blockItem)
        {
            BlockPos placePos = context.getClickedPos().relative(context.getClickedFace());
            if (level.getBlockState(placePos).getBlock() == FBlocks.FOAM.get())
            {
                if (!level.isClientSide)
                {
                    event.getEntity().awardStat(Stats.ITEM_USED.get(blockItem));
                    ItemUtils.createFilledResult(stack, event.getEntity(), new ItemStack(FItems.BOTTLE_OF_FOAM.get()));
                }
            }
        }
    }
}
