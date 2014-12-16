package me.chasertw123.villagedefense;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.chasertw123.villagedefense.commands.VillageDefenseCmd;
import me.chasertw123.villagedefense.exceptions.VillageDefenseException;
import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.arena.RoleSelect;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.building.BuildingArmorsmith;
import me.chasertw123.villagedefense.game.building.BuildingBrewery;
import me.chasertw123.villagedefense.game.building.BuildingButcher;
import me.chasertw123.villagedefense.game.building.BuildingChurch;
import me.chasertw123.villagedefense.game.building.BuildingFarmer;
import me.chasertw123.villagedefense.game.building.BuildingTownhall;
import me.chasertw123.villagedefense.game.building.BuildingWeaponsmith;
import me.chasertw123.villagedefense.game.enemy.Tank;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.listeners.EntityTarget;
import me.chasertw123.villagedefense.listeners.PlayerInteractEntity;
import me.chasertw123.villagedefense.utils.LocationUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private final String prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "VD" + ChatColor.WHITE + "]" + ChatColor.RESET + " ";
    private FileConfiguration arenayml;
    private File arenaFile = new File(getDataFolder().getAbsoluteFile() + File.separator + "arena.yml");
    private Game game;

    public void onEnable() {

        System.out.println("You are about to witness the evolution of something awesome.");

        getServer().getPluginManager().registerEvents(new EntityTarget(this), this);

        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingArmorsmith.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingBrewery.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingButcher.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingChurch.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingTownhall.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingWeaponsmith.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingFarmer.class);

        if (!this.getConfig().getBoolean("disabled-roles.archer"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Archer.class, EntityType.SKELETON);

        if (!this.getConfig().getBoolean("disabled-roles.assassin"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Assassin.class, EntityType.CREEPER);

        if (!this.getConfig().getBoolean("disabled-roles.healer"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Healer.class, EntityType.SLIME);

        if (!this.getConfig().getBoolean("disabled-roles.mage"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Mage.class, EntityType.WITCH);

        if (!this.getConfig().getBoolean("disabled-roles.tank"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Tank.class, EntityType.IRON_GOLEM);

        this.getServer().getPluginManager().registerEvents(new PlayerInteractEntity(this), this);
        this.getCommand("VillageDefense").setExecutor(new VillageDefenseCmd(this));

        new File(getDataFolder().getAbsolutePath() + File.separator + "schematics" + File.separator).mkdirs();

        try {
            arenaFile.createNewFile();
        } catch (IOException e) {
            sendConsoleSevere("Failed to generate arena.yml!");
        }

        arenayml = YamlConfiguration.loadConfiguration(arenaFile);

        try {

            for (Class<? extends Role> r : Role.roleClasses.keySet())
                new RoleSelect(LocationUtils.deserializeLoc(arenayml.getString("roleselector." + r.getSimpleName())), Role.roleClasses.get(r), r).spawnEntity();

            ArrayList<Building> buildings = new ArrayList<Building>();
            ArrayList<Location> enemyLocations = new ArrayList<Location>();

            buildings.add(new BuildingArmorsmith(Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(20, 0, 0)));
            buildings.add(new BuildingBrewery(Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(20, 0, 10)));
            buildings.add(new BuildingButcher(Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(20, 0, 20)));
            buildings.add(new BuildingChurch(Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(20, 0, 30)));
            buildings.add(new BuildingFarmer(Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(20, 0, 40)));
            buildings.add(new BuildingTownhall(Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(20, 0, 50)));
            buildings.add(new BuildingWeaponsmith(Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(20, 0, 60)));
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

        for (RoleSelect rs : RoleSelect.roleSelectObjects)
            if (rs.getEntity() != null) {
                for (Entity e : rs.getEntity().getNearbyEntities(1, 1, 1))
                    if (e.getPassenger() == rs.getEntity())
                        e.remove();

                rs.getEntity().remove();
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

    /** YAML Manager **/

    public FileConfiguration getArenaConfig() {
        return arenayml;
    }

    public void saveArenaConfig() {
        try {
            arenayml.save(arenaFile);
        } catch (IOException e) {
            sendConsoleSevere("Failed to save arena.yml!");
        }
    }
}
