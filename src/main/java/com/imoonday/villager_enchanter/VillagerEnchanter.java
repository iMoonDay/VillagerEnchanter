package com.imoonday.villager_enchanter;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.stream.IntStream;

public class VillagerEnchanter implements ModInitializer {

    private static final Identifier ENCHANTER_ID = id("enchanter");
    public static final RegistryKey<PointOfInterestType> ENCHANTER_TYPE = RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, ENCHANTER_ID);
    public static final VillagerProfession ENCHANTER = Registry.register(Registries.VILLAGER_PROFESSION, ENCHANTER_ID, new VillagerProfession("enchanter", entry -> entry.matchesKey(ENCHANTER_TYPE), entry1 -> entry1.matchesKey(ENCHANTER_TYPE), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN));
    public static final Item ENCHANT_REFRESH_BOOK = Items.register(id("enchant_refresh_book"), new EnchantRefreshBookItem(new FabricItemSettings().maxCount(16)));

    @Override
    public void onInitialize() {
        PointOfInterestHelper.register(ENCHANTER_ID, 1, 1, Blocks.ENCHANTING_TABLE);
        TradeOfferHelper.registerVillagerOffers(ENCHANTER, 1, factories -> factories.add(new TradeOffers.BuyForOneEmeraldFactory(Items.LAPIS_LAZULI, 8, 16, 2)));
        IntStream.of(1, 2, 2, 3, 3, 4, 4, 5).forEach(i -> TradeOfferHelper.registerVillagerOffers(ENCHANTER, i, factories -> factories.add(new TradeOffers.EnchantBookFactory(15))));
        TradeOfferHelper.registerVillagerOffers(ENCHANTER, 5, factories -> factories.add(new TradeOffers.ProcessItemFactory(Items.BOOK, 1, 16, ENCHANT_REFRESH_BOOK, 1, 4, 15)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.LIBRARIAN, 5, factories -> factories.add(new TradeOffers.ProcessItemFactory(Items.BOOK, 1, 16, ENCHANT_REFRESH_BOOK, 1, 4, 15)));
    }

    public static Identifier id(String id) {
        return new Identifier("villager_enchanter", id);
    }
}
