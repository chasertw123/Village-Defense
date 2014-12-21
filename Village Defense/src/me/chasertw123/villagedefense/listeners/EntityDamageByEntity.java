package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.role.RoleSelect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {

    @SuppressWarnings("unused")
    private Main plugin;

    public EntityDamageByEntity(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        for (RoleSelect rs : RoleSelect.roleSelectObjects)
            if (rs.getEntity() == event.getDamager() || rs.getEntity() == event.getEntity())
                event.setCancelled(true);
    }
}
