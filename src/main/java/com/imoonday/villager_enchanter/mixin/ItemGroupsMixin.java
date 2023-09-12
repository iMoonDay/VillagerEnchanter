package com.imoonday.villager_enchanter.mixin;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemGroups.class)
public interface ItemGroupsMixin {

    @Accessor("TOOLS")
    static ItemGroup getTools(){
        throw new AssertionError();
    }
}
