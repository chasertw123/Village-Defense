package me.chasertw123.villagedefense.stats.achievements.standard;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.stats.achievements.Achievement;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class BetaTester extends Achievement implements Listener {

    public BetaTester(Main plugin) {
        super("default.betatester", "Beta Tester", "Beta Tester", "Be part of Village Defense beta!", plugin);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.getDescription().getVersion().toLowerCase().contains("beta"))
            plugin.getStatsManager().setAchievement(this, event.getPlayer(), true);
    }
}