package com.imoonday.villager_enchanter.mixin;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.imoonday.villager_enchanter.VillagerEnchanter.ENCHANT_REFRESH_BOOK;

@Mixin(ItemGroups.class)
public class ItemGroupsMixin {

    @Shadow
    @Final
    private static RegistryKey<ItemGroup> TOOLS;

    @Inject(method = "registerAndGetDefault", at = @At("TAIL"))
    private static void addBook(Registry<ItemGroup> registry, CallbackInfoReturnable<ItemGroup> cir) {
        ItemGroupEvents.modifyEntriesEvent(TOOLS).register(entries -> entries.add(ENCHANT_REFRESH_BOOK));
    }
}
