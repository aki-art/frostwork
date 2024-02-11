package io.github.akiart.frostwork.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Mixin(RandomState.class)
public class RandomStateMixin {
    // wrapNew(Lnet/minecraft/world/level/levelgen/DensityFunction;)Lnet/minecraft/world/level/levelgen/DensityFunction;
    @Inject(method = "onSheared", at = @At("RETURN"), remap = false, cancellable = true)
    private void onSheared(@Nullable Player player, @Nonnull ItemStack item, Level world, BlockPos pos, int fortune, CallbackInfoReturnable<List<ItemStack>> ci) {
        System.out.println("This line is printed by an example mod mixin!");
    }
}
