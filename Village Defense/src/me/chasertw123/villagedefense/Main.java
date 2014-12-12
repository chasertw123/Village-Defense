package me.chasertw123.villagedefense;

import java.util.ArrayList;

import me.chasertw123.villagedefense.exceptions.AbilityCreationException;
import me.chasertw123.villagedefense.exceptions.VillageDefenseException;
import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.building.BuildingFarmer;
import me.chasertw123.villagedefense.game.enemy.Tank;
import me.chasertw123.villagedefense.game.role.Healer;
import me.chasertw123.villagedefense.game.role.Role;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private final String prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "VD" + ChatColor.WHITE + "]" + ChatColor.RESET;
    private Game game;
    static Main i;

    public void onEnable() {

        i = this;
        this.getServer().getPluginManager().registerEvents(this, this);

        System.out.println("You are about to witness the evolution of something awesome.");

        try {
            ArrayList<Building> buildings = new ArrayList<>();

            buildings.add(new BuildingFarmer(Bukkit.getWorlds().get(0).getSpawnLocation().clone().add(20, 0, 0)));

            game = new Game(new Arena(buildings, Bukkit.getWorlds().get(0).getSpawnLocation().clone()), 1, 1);

            Tank t = new Tank();

            t.spawnEntity(Bukkit.getWorlds().get(0).getSpawnLocation(), this);

        } catch (VillageDefenseException e) {
            e.printStackTrace();
        }
    }

    public void onDisable() {
        for (Building b : game.getArena().getBuildings())
            b.getVillager().getVil().remove();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();

        try {
            GamePlayer gp = new GamePlayer(new Healer(), p);

            Role role = gp.getRole();
            event.getPlayer().getInventory().setItem(0, role.getItemStack());
            event.getPlayer().getInventory().setItem(1, role.getPrimaryAbility().getItemStack());
            event.getPlayer().getInventory().setItem(2, role.getSecondaryAbility().getItemStack());
            event.getPlayer().getInventory().setItem(3, role.getTertiaryAbility().getItemStack());
            event.getPlayer().getInventory().setItem(4, role.getUltraAbility().getItemStack());
            event.getPlayer().getInventory().setHelmet(role.getBanner());

        } catch (AbilityCreationException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() == game.getArena().getBuildings().get(0).getVillager().getVil()) {
            game.getArena().getBuildings().get(0).levelUp();
            Bukkit.broadcastMessage(event.getPlayer().getName() + " upgarded Farm to tier " + game.getArena().getBuildings().get(0).getTier());
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

    public static JavaPlugin getInstance() {
        return i;
    }
}
