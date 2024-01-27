package io.github.akiart.frostwork.common.attachments;

import io.github.akiart.frostwork.Frostwork;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Frostwork.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Boolean>> WAS_IN_ACID = ATTACHMENT_TYPES.register("was_in_acid", () -> AttachmentType.builder(() -> false).build());
}
