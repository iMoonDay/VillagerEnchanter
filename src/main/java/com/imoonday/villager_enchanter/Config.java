package com.imoonday.villager_enchanter;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = VillagerEnchanter.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue BOOK_PRICE = BUILDER
            .comment("The price of Enchant Refresh Book")
            .comment("附魔刷新书的绿宝石价格")
            .defineInRange("bookPrice", 16, 1, 64);
    private static final ForgeConfigSpec.IntValue BOOK_TRADE_COUNT = BUILDER
            .comment("The maximum number of transactions for the Enchant Refresh Book")
            .comment("附魔刷新书的最大交易数")
            .defineInRange("bookTradeCount", 4, 1, Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int bookPrice;
    public static int bookTradeCount;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        bookPrice = BOOK_PRICE.get();
        bookTradeCount = BOOK_TRADE_COUNT.get();
    }
}
