package com.imoonday.villager_enchanter.mixin;

import com.imoonday.villager_enchanter.VillagerEnchanter;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {

    @Redirect(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    public boolean isOf(ItemStack instance, Item item) {
        return instance.isOf(Items.VILLAGER_SPAWN_EGG) || instance.isOf(VillagerEnchanter.ENCHANT_REFRESH_BOOK);
    }
}
