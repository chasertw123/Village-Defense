package me.chasertw123.villagedefense.utils;

import org.bukkit.inventory.ItemStack;

public class ItemStackUtils {

    public static boolean areItemStacksSimilar(ItemStack i1, ItemStack i2) {

        if (i1 == null || i2 == null)
            return false;

        if (i1.getType() != i2.getType())
            return false;

        if (i1.getDurability() != i2.getDurability())
            return false;

        if (i1.hasItemMeta() && !i2.hasItemMeta())
            return false;

        if (!i1.hasItemMeta() && i2.hasItemMeta())
            return false;

        if (i1.getItemMeta().hasDisplayName() && !i2.getItemMeta().hasDisplayName())
            return false;

        if (!i1.getItemMeta().hasDisplayName() && i2.getItemMeta().hasDisplayName())
            return false;

        if (!i1.getItemMeta().getDisplayName().equals(i2.getItemMeta().getDisplayName()))
            return false;

        return true;
    }
}
