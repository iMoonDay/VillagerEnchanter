package com.imoonday.villager_enchanter;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VillagerEnchanter.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEvents {

    @SubscribeEvent
    public static void initTab(CreativeModeTabEvent.BuildContents contents) {
        if (contents.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            contents.accept(VillagerEnchanter.ENCHANT_REFRESH_BOOK);
        }
    }
}
