package me.chasertw123.villagedefense.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils {

    public static String serializeLoc(Location l) {

        return l.getWorld().getName() + ", " + l.getX() + ", " + l.getY() + ", " + l.getZ() + ", " + l.getYaw() + ", " + l.getPitch();
    }

    public static Location deserializeLoc(String s) {

        String[] st = s.split(", ");

        return new Location(Bukkit.getWorld(st[0]), Double.parseDouble(st[1]), Double.parseDouble(st[2]), Double.parseDouble(st[3]), Float.parseFloat(st[4]), Float.parseFloat(st[5]));
    }
}
