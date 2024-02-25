package io.github.akiart.frostwork.common.item.itemTypes;

import io.github.akiart.frostwork.common.entity.entityTypes.WurmArrow;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class WurmArrowItem extends ArrowItem {

    public WurmArrowItem(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity shooter) {
        return new WurmArrow(level, shooter, itemStack.copyWithCount(1));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag pFlag) {
        tooltip.add(Component.translatable(MobEffects.POISON.getDescriptionId()).withColor(0x0000FF));//.withStyle(ChatFormatting.GREEN));
    }
}
