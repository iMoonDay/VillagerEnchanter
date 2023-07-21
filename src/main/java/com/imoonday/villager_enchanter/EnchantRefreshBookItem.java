package com.imoonday.villager_enchanter;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.World;

public class TradeRefreshBookItem extends Item {

    public TradeRefreshBookItem(Settings settings) {
        super(settings);
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
                if (tradeOffer.getSellItem().isOf(Items.ENCHANTED_BOOK)) {
                    offers.set(i, new TradeOffers.EnchantBookFactory(15).create(villager, villager.getRandom()));
                }
            }
            villager.setOffers(offers);
            user.getStackInHand(hand).decrement(1);
        }
        return ActionResult.success(world.isClient);
    }
}
