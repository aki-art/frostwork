package io.github.akiart.frostwork.common;

import io.github.akiart.frostwork.Frostwork;
import io.github.akiart.frostwork.common.entity.entityTypes.ThrownLavaPotion;
import io.github.akiart.frostwork.common.entity.entityTypes.WurmArrow;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Frostwork.MOD_ID);
    public static final DeferredHolder<EntityType<?>, EntityType<WurmArrow>> WURM_ARROW = ENTITY_TYPES.register(
            "wurm_arrow", () -> EntityType.Builder.<WurmArrow>of(WurmArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("wurm_arrow")
    );


    public static final DeferredHolder<EntityType<?>, EntityType<ThrownLavaPotion>> THROWN_LAVA_POTION = ENTITY_TYPES.register(
            "thrown_lava_potion",
            () -> EntityType.Builder.<ThrownLavaPotion>of(ThrownLavaPotion::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("thrown_lava_potion")
    );

//    public static final DeferredHolder<EntityType<?>, EntityType<IcicleEntity>> ICICLE = ENTITY_TYPES.register(
//            "icicle",
//            () -> EntityType.Builder
//                    .<IcicleEntity>of(IcicleEntity::new, MobCategory.MISC)
//                    .sized(0.5f, 0.5f)
//                    .clientTrackingRange(4)
//                    .updateInterval(20)
//                    .build(new ResourceLocation(Frostwork.MOD_ID, "icicle").toString()));
}
