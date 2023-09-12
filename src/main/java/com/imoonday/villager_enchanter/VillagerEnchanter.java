package com.imoonday.villager_enchanter;

import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VillagerEnchanter.MODID)
public class VillagerEnchanter {

    public static final String MODID = "villager_enchanter";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> ENCHANT_REFRESH_BOOK = ITEMS.register("enchant_refresh_book", () -> new EnchantRefreshBookItem(new Item.Properties().stacksTo(16)));

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, MODID);
    public static final RegistryObject<PoiType> ENCHANTING_TABLE_POI_TYPE = POI_TYPES.register("enchanter", () -> new PoiType(Set.of(Blocks.ENCHANTING_TABLE.defaultBlockState()), 1, 1));

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, MODID);
    public static final RegistryObject<VillagerProfession> ENCHANTER_PROFESSION = VILLAGER_PROFESSIONS.register("enchanter", () -> new VillagerProfession("enchanter", poiTypeHolder -> poiTypeHolder.is(ENCHANTING_TABLE_POI_TYPE.getId()), poiTypeHolder -> poiTypeHolder.is(ENCHANTING_TABLE_POI_TYPE.getId()), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LIBRARIAN));

    public VillagerEnchanter() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        POI_TYPES.register(modEventBus);
        VILLAGER_PROFESSIONS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
