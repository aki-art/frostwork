package io.github.akiart.frostwork.common.item.itemTypes;

import io.github.akiart.frostwork.common.entity.entityTypes.ThrownLavaPotion;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.level.Level;

public class LavaPotionItem extends PotionItem {
    public LavaPotionItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            ThrownLavaPotion thrownpotion = new ThrownLavaPotion(level, player);
            thrownpotion.setItem(itemstack);
            thrownpotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
            level.addFreshEntity(thrownpotion);
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        if (!player.getAbilities().instabuild)
            itemstack.shrink(1);

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
