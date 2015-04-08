package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.game.scoreboard.ScoreboardType;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
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
                                gp.getRole().setExperience((int) value.value() + gp.getRole().getExperience());
                                break;
                            }

                    Role role = gp.getRole();

                    if (role.getExperience() >= role.getLevelUpExperience(role.getLevel() + 1)) {

                        role.setExperience(role.getExperience() - role.getLevelUpExperience(role.getLevel() + 1));
                        role.levelUp();

                        gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "You leveled up to level " + ChatColor.BLUE + role.getLevel() + "! Shift right-click with any ability in hand to level it up!");
                        gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
                    }

                    plugin.getScoreboardManager().updateScoreboard(ScoreboardType.INGAME);
                }
            }
    }
}
