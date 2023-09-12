package com.imoonday.villager_enchanter;

import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.stream.Collectors;

public class EnchantRefreshBookItem extends Item {
    public EnchantRefreshBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
        if (!(entity instanceof Villager villager)) {
            return super.interactLivingEntity(stack, user, entity, hand);
        }
        Level world = user.level;
        if (!world.isClientSide) {
            MerchantOffers offers = villager.getOffers();
            for (int i = 0; i < offers.size(); i++) {
                MerchantOffer tradeOffer = offers.get(i);
                ItemStack sellItem = tradeOffer.getResult();
                if (tradeOffer.getBaseCostA().is(Items.EMERALD) && tradeOffer.getCostB().is(Items.BOOK) && sellItem.is(Items.ENCHANTED_BOOK)) {
                    ItemStack stackInHand = user.getItemInHand(hand);
                    if (stackInHand.isEmpty()) {
                        break;
                    }
                    offers.set(i, new EnchantBookForEmeralds(15).getOffer(villager, villager.getRandom()));
                    stackInHand.shrink(1);
                }
            }
            villager.setOffers(offers);
        }
        villager.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 1.0f, 1.0f);
        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    static class EnchantBookForEmeralds implements VillagerTrades.ItemListing {
        private final int villagerXp;

        public EnchantBookForEmeralds(int p_35683_) {
            this.villagerXp = p_35683_;
        }

        public MerchantOffer getOffer(Entity p_219688_, RandomSource p_219689_) {
            List<Enchantment> list = Registry.ENCHANTMENT.stream().filter(Enchantment::isTradeable).collect(Collectors.toList());
            Enchantment enchantment = list.get(p_219689_.nextInt(list.size()));
            int i = Mth.nextInt(p_219689_, enchantment.getMinLevel(), enchantment.getMaxLevel());
            ItemStack itemstack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, i));
            int j = 2 + p_219689_.nextInt(5 + i * 10) + 3 * i;
            if (enchantment.isTreasureOnly()) {
                j *= 2;
            }

            if (j > 64) {
                j = 64;
            }

            return new MerchantOffer(new ItemStack(Items.EMERALD, j), new ItemStack(Items.BOOK), itemstack, 12, this.villagerXp, 0.2F);
        }
    }
}
