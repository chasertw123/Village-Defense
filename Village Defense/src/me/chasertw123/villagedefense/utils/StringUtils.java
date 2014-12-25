package me.chasertw123.villagedefense.utils;

import org.bukkit.ChatColor;

public class StringUtils {

    public static String fancyTierString(int tier) {
        return ChatColor.BLUE + "" + ChatColor.BOLD + " [" + ChatColor.GOLD + ChatColor.BOLD + "Level " + tier + ChatColor.BLUE + "" + ChatColor.BOLD + "]";
    }
}
