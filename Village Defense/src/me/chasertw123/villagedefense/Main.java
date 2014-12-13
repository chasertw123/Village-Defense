package me.chasertw123.villagedefense;

import java.io.File;
import java.util.ArrayList;

import me.chasertw123.villagedefense.exceptions.VillageDefenseException;
import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.building.BuildingFarmer;
import me.chasertw123.villagedefense.game.enemy.Tank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private final String prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "VD" + ChatColor.WHITE + "]" + ChatColor.RESET;
    private Game game;

    public void onEnable() {

        System.out.println("You are about to witness the evolution of something awesome.");

        this.getServer().getPluginManager().registerEvents(this, this);

        new File(getDataFolder().getAbsolutePath() + File.separator + "schematics" + File.separator).mkdirs();

        try {

            ArrayList<Building> buildings = new ArrayList<Building>();
            ArrayList<Location> enemyLocations = new ArrayList<Location>();

            buildings.add(new BuildingFarmer(Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(20, 0, 0)));
            enemyLocations.add(Bukkit.getWorlds().get(0).getSpawnLocation().clone());

            game = new Game(new Arena(buildings, enemyLocations, Bukkit.getWorlds().get(0).getSpawnLocation().clone(), this), 1, 1);

            Tank t = new Tank();
            t.spawnEntity(Bukkit.getWorlds().get(0).getSpawnLocation(), this);

        } catch (VillageDefenseException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {

        for (Building b : game.getArena().getBuildings())
            if (b.getVillager().getVil() != null) {
                for (Entity e : b.getVillager().getVil().getNearbyEntities(1, 1, 1))
                    if (e.getPassenger() == b.getVillager().getVil())
                        e.remove();

                b.getVillager().getVil().remove();
            }
    }

    @EventHandler
    public void onPlayerJoin(PlayerInteractEntityEvent event) {

        if (event.getRightClicked() == game.getArena().getBuildings().get(0).getVillager().getVil()) {

            event.setCancelled(true);

            if (game.getArena().getBuildings().get(0).getTier() < game.getArena().getBuildings().get(0).getMaxTier()) {
                game.getArena().getBuildings().get(0).levelUp(this);
                Bukkit.broadcastMessage(event.getPlayer().getName() + " upgarded Farm to tier " + game.getArena().getBuildings().get(0).getTier());
            }
        }
    }

    /** Console Messages **/

    public void sendConsoleInfo(String message) {
        this.getLogger().info(message);
    }

    public void sendConsoleWarning(String message) {
        this.getLogger().warning(message);
    }

    public void sendConsoleSevere(String message) {
        this.getLogger().severe(message);
    }

    /** Getters and Setters **/

    public String getPrefix() {
        return prefix;
    }

    public Game getGame() {
        return game;
    }
}
