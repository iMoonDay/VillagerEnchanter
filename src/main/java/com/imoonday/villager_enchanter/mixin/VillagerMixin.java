package com.imoonday.villager_enchanter.mixin;

import com.imoonday.villager_enchanter.VillagerEnchanter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Villager.class)
public class VillagerMixin {

    @Inject(method = "mobInteract", at = @At(value = "HEAD"), cancellable = true)
    public void modInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (player.getItemInHand(hand).is(VillagerEnchanter.ENCHANT_REFRESH_BOOK.get())) {
            cir.setReturnValue(InteractionResult.PASS);
        }
    }
}
