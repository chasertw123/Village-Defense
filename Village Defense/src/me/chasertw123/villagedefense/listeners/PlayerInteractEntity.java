package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.arena.RoleSelect;
import me.chasertw123.villagedefense.game.building.Building;

import org.bukkit.Bukkit;
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

        /* Role selecting */
        for (RoleSelect rs : RoleSelect.roleSelectObjects)
            if (event.getRightClicked().equals(rs.getEntity()))
                try {
                    event.getPlayer().sendMessage(plugin.getPrefix() + "You have selected the " + rs.getRole().getSimpleName() + " role!");

                    for (GamePlayer gp : plugin.getGame().getPlayers())
                        if (gp.getPlayer().equals(event.getPlayer()))
                            gp.setRole(rs.getRole().newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                }
    }
}
