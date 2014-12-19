package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.role.RoleSelect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTarget implements Listener {

    @SuppressWarnings("unused")
    private Main plugin;

    public EntityTarget(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {

        for (RoleSelect rs : RoleSelect.roleSelectObjects)
            if (event.getEntity() == rs.getEntity())
                event.setCancelled(true);
    }
}
