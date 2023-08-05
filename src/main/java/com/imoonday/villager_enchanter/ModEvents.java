package com.imoonday.villager_enchanter;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.stream.IntStream;

@Mod.EventBusSubscriber(modid = VillagerEnchanter.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void initTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.LIBRARIAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack stack = new ItemStack(VillagerEnchanter.ENCHANT_REFRESH_BOOK.get(), 1);
            int villagerLevel = 5;
            trades.get(villagerLevel).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 16), new ItemStack(Items.BOOK), stack, 4, 15, 0.05f));
        }

        if (event.getType() == VillagerEnchanter.ENCHANTER_PROFESSION.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).add((entity, random) -> new MerchantOffer(new ItemStack(Items.LAPIS_LAZULI, 8), new ItemStack(Items.EMERALD), 16, 2, 0.05f));
            IntStream.of(1, 2, 2, 3, 3, 4, 4, 5).forEach(i -> trades.get(i).add((entity, random) -> new EnchantRefreshBookItem.EnchantBookForEmeralds(15).getOffer(entity, random)));
            trades.get(5).add((entity, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, Config.bookPrice), new ItemStack(Items.BOOK), new ItemStack(VillagerEnchanter.ENCHANT_REFRESH_BOOK.get()), Config.bookTradeCount, 15, 0.05f));
        }
    }
}
