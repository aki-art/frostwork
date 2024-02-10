package io.github.akiart.frostwork.common.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;

public class FSoundTypes {
    public static final SoundType CANDELOUPE = new DeferredSoundType(
            1.0F,
            1.1F,
            () -> SoundEvents.FUNGUS_BREAK,
            () -> SoundEvents.FUNGUS_STEP,
            () -> SoundEvents.FUNGUS_PLACE,
            () -> SoundEvents.CANDLE_HIT,
            () -> SoundEvents.FUNGUS_FALL
    );
}
