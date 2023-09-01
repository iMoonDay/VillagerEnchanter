package com.imoonday.villager_enchanter;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnchantRefreshBookItem extends Item {

    public EnchantRefreshBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!(entity instanceof VillagerEntity villager)) {
            return super.useOnEntity(stack, user, entity, hand);
        }
        World world = user.getWorld();
        if (!world.isClient) {
            TradeOfferList offers = villager.getOffers();
            for (int i = 0; i < offers.size(); i++) {
                TradeOffer tradeOffer = offers.get(i);
                ItemStack sellItem = tradeOffer.getSellItem();
                if (tradeOffer.getOriginalFirstBuyItem().isOf(Items.EMERALD) && tradeOffer.getSecondBuyItem().isOf(Items.BOOK) && sellItem.isOf(Items.ENCHANTED_BOOK)) {
                    ItemStack stackInHand = user.getStackInHand(hand);
                    if (stackInHand.isEmpty()) {
                        break;
                    }
                    offers.set(i, new TradeOffers.EnchantBookFactory(15).create(villager, villager.getRandom()));
                    stackInHand.decrement(1);
                }
            }
            villager.setOffers(offers);
        }
        villager.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);
        return ActionResult.success(world.isClient);
    }
}
