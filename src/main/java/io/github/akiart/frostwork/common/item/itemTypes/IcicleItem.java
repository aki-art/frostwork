package io.github.akiart.frostwork.common.item.itemTypes;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class IcicleItem extends BlockItem {
    public IcicleItem(Block block, Properties properties) {
        super(block, properties);
        //DispenserBlock.registerBehavior(this, new DispenseIcicleBehavior());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);
        tooltip.add(Component.literal("When thrown").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal(" 3 Attack Damage").withStyle(ChatFormatting.DARK_PURPLE)); // todo: translatable
    }

    @Override
    public InteractionResult useOn(@NotNull UseOnContext context) {
        InteractionResult result = super.useOn(context);

        if(result == InteractionResult.FAIL) {
            Player player = context.getPlayer();

            if(player != null)
                return use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
        }

        return result;
    }

    private void damageItem(ItemStack itemStack, LivingEntity userEntity, Player player) {
        itemStack.hurtAndBreak(1, player, (entity) -> {
            entity.broadcastBreakEvent(userEntity.getUsedItemHand());
        });
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

//        if (!level.isClientSide) {
//            IcicleEntity icicle = new IcicleEntity(FEntityTypes.ICICLE.get(), player, level, itemstack);
//            icicle.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
//            icicle.pickup = AbstractArrow.Pickup.ALLOWED;
//            level.addFreshEntity(icicle);
//        }

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        level.playSound(null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.SNOWBALL_THROW,
                SoundSource.NEUTRAL,
                1.0F,
                0.4F / (level.random.nextFloat() * 0.4F + 0.8F));

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

}
