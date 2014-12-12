package me.chasertw123.villagedefense.utils;

import java.io.File;

import me.chasertw123.villagedefense.game.building.Building;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;

@SuppressWarnings("deprecation")
public class SchematicUtil {

    public static void build(File file, Building b) {
        try {
            EditSession es = new EditSession(new BukkitWorld(b.getCenter().getWorld()), 999999999);
            CuboidClipboard cc = CuboidClipboard.loadSchematic(file);
            Vector v = new Vector(b.getCenter().getX(), b.getCenter().getY(), b.getCenter().getZ());
            cc.paste(es, v, false);

            for (int x = v.getBlockX(); x < cc.getWidth(); x++)
                for (int y = v.getBlockY(); y < cc.getHeight(); y++)
                    for (int z = v.getBlockZ(); z < cc.getLength(); z++) {
                        Block bl = b.getCenter().getWorld().getBlockAt(x, y, z);
                        if (bl.getType() == Material.SPONGE) {
                            bl.setType(Material.AIR);
                            b.getVillager().setLoc(new Location(b.getCenter().getWorld(), x, y, z));
                            b.getVillager().spawnVillager();
                        }
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
