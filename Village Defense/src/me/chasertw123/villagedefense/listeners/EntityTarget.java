package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.role.RoleSelect;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTarget implements Listener {

    private Main plugin;

    public EntityTarget(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {

        for (RoleSelect rs : RoleSelect.roleSelectObjects)
            if (event.getEntity() == rs.getEntity())
                event.setCancelled(true);

        if (plugin.getGame().getWave() != null)
            for (LivingEntity le : plugin.getGame().getWave().getEnemies())
                if (event.getEntity() == le && !(event.getTarget() instanceof Player)) {

                    if (plugin.getGame().getPlayers().size() == 0) {
                        event.setCancelled(true);
                        return;
                    }

                    event.setTarget(getClosestGamePlayer(event.getEntity()));
                }

    }

    private Entity getClosestGamePlayer(Entity e) {

        GamePlayer gp = null;
        double distance = -1;

        for (GamePlayer p : plugin.getGame().getPlayers()) {

            double d = p.getPlayer().getLocation().distanceSquared(e.getLocation());

            if (distance == -1) {
                distance = d;
                gp = p;
            }

            else if (d < distance) {
                distance = d;
                gp = p;
            }
        }

        return gp.getPlayer();
    }
}
