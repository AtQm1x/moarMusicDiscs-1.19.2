package com.atmx.moarmusicdiscs.items;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class modCreativeModeTab {
    public static final CreativeModeTab DISC_TAB = new CreativeModeTab("custom_disc_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            //return new ItemStack(modItems.DISC_HONK_ITEM.get());
            return new ItemStack(Items.MUSIC_DISC_CAT);
        }
    };
}