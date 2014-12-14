package me.chasertw123.villagedefense;

import java.io.File;
import java.util.ArrayList;

import me.chasertw123.villagedefense.commands.VillageDefenseCmd;
import me.chasertw123.villagedefense.exceptions.VillageDefenseException;
import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.building.BuildingFarmer;
import me.chasertw123.villagedefense.game.enemy.Tank;
import me.chasertw123.villagedefense.game.role.Role;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private final String prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "VD" + ChatColor.WHITE + "]" + ChatColor.RESET;
    private Game game;

    public void onEnable() {

        System.out.println("You are about to witness the evolution of something awesome.");

        Role.registerRole(me.chasertw123.villagedefense.game.role.Archer.class);
        Role.registerRole(me.chasertw123.villagedefense.game.role.Assassin.class);
        Role.registerRole(me.chasertw123.villagedefense.game.role.Healer.class);
        Role.registerRole(me.chasertw123.villagedefense.game.role.Mage.class);
        Role.registerRole(me.chasertw123.villagedefense.game.role.Tank.class);

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("VillageDefense").setExecutor(new VillageDefenseCmd(this));

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
