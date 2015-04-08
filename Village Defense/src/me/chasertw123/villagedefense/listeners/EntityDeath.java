package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.scoreboard.ScoreboardType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.MetadataValue;

public class EntityDeath implements Listener {

    private Main plugin;

    public EntityDeath(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if (plugin.getGame() != null && plugin.getGame().getWave() != null)
            if (plugin.getGame().getWave().getEnemies().contains(event.getEntity())) {

                if (event.getEntity().getKiller() instanceof Player) {

                    GamePlayer gp = plugin.getGame().getGamePlayer((Player) event.getEntity().getKiller());

                    if (event.getEntity().hasMetadata("gold"))
                        for (MetadataValue value : event.getEntity().getMetadata("gold"))
                            if (value.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())) {
                                gp.incrementGold((int) value.value());
                                break;
                            }

                    if (event.getEntity().hasMetadata("experience"))
                        for (MetadataValue value : event.getEntity().getMetadata("experience"))
                            if (value.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())) {
                                gp.getRole().setRoleExperience((int) value.value() + gp.getRole().getRoleExperience());
                                break;
                            }

                    plugin.getScoreboardManager().updateScoreboard(ScoreboardType.INGAME);
                }

                // TOFO: Check if can level up
            }
    }
}
