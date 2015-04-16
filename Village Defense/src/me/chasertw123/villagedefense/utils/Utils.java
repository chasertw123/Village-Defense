package me.chasertw123.villagedefense.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static String serializeLoc(Location l) {

        return l.getWorld().getName() + ", " + l.getX() + ", " + l.getY() + ", " + l.getZ() + ", " + l.getYaw() + ", " + l.getPitch();
    }

    public static Location deserializeLoc(String s) {

        String[] st = s.split(", ");

        return new Location(Bukkit.getWorld(st[0]), Double.parseDouble(st[1]), Double.parseDouble(st[2]), Double.parseDouble(st[3]), Float.parseFloat(st[4]), Float.parseFloat(st[5]));
    }

    public static String fancyTierString(int tier) {
        return ChatColor.BLUE + "" + ChatColor.BOLD + " [" + ChatColor.GOLD + ChatColor.BOLD + "Level " + tier + ChatColor.BLUE + "" + ChatColor.BOLD + "]";
    }

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

    public static boolean itemStacksHaveSameName(ItemStack i1, ItemStack i2) {

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
