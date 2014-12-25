package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.game.role.RoleSelect;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity implements Listener {

    private Main plugin;

    public PlayerInteractEntity(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

        /* Upgrading buildings, debug code */
        for (Building b : plugin.getGame().getArena().getBuildings())
            if (event.getRightClicked() == b.getVillager().getVil()) {

                if (b.getTier() < b.getMaxTier()) {
                    b.levelUp(plugin);
                    Bukkit.broadcastMessage(plugin.getPrefix() + event.getPlayer().getName() + " upgarded " + b.getName() + " to tier " + b.getTier());
                }

                event.setCancelled(true);
            }

        if (plugin.getGame().getGameState() == GameState.LOBBY || plugin.getGame().getGameState() == GameState.STARTING)
            for (RoleSelect rs : RoleSelect.roleSelectObjects)
                if (event.getRightClicked().equals(rs.getEntity()))
                    try {
                        for (GamePlayer gp : plugin.getGame().getPlayers())
                            if (gp.isEqualToPlayer(event.getPlayer())) {

                                Role role = rs.getRole().newInstance();

                                if (gp.getRole() == null || !gp.getRole().getName().equals(role.getName())) {
                                    gp.setRole(role);
                                    event.getPlayer().sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You have selected the " + ChatColor.BLUE + role.getName() + ChatColor.YELLOW + " role!");
                                }

                                else
                                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You already have the role " + ChatColor.BLUE + role.getName() + ChatColor.YELLOW + " selected!");
                            }
                    } catch (InstantiationException | IllegalAccessException e) {
                    }
    }
}
