package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.abilities.ArrowStorm;
import me.chasertw123.villagedefense.game.role.RoleSelect;

import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntity implements Listener {

    private Main plugin;

    public EntityDamageByEntity(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        for (RoleSelect rs : RoleSelect.roleSelectObjects)
            if (rs.getEntity() == event.getDamager() || rs.getEntity() == event.getEntity()) {
                event.setCancelled(true);
                return;
            }

        if (event.getEntity() instanceof Arrow) {

            Arrow a = (Arrow) event.getEntity();

            for (GamePlayer gp : plugin.getGame().getPlayers())
                if (gp.getRole() != null && gp.getRole().getName().equals("Archer")) {

                    ArrowStorm as = (ArrowStorm) gp.getRole().getPrimaryAbility();

                    if (as.arrowStormArrows.contains(a.getUniqueId())) {
                        event.setDamage(event.getDamage() * (as.getTier() * 1.5));
                        a.remove();
                    }
                }

        }
    }
}
