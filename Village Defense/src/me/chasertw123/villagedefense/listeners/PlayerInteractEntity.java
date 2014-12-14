package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;

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
    public void onPlayerJoin(PlayerInteractEntityEvent event) {

        if (event.getRightClicked() == plugin.getGame().getArena().getBuildings().get(0).getVillager().getVil()) {

            if (plugin.getGame().getArena().getBuildings().get(0).getTier() < plugin.getGame().getArena().getBuildings().get(0).getMaxTier()) {

                plugin.getGame().getArena().getBuildings().get(0).levelUp(plugin);
                Bukkit.broadcastMessage(event.getPlayer().getName() + " upgarded Farm to tier " + plugin.getGame().getArena().getBuildings().get(0).getTier());
            }

            event.setCancelled(true);
        }
    }
}
