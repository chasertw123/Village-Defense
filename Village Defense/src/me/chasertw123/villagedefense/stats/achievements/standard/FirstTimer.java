package me.chasertw123.villagedefense.stats.achievements.standard;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.events.AchievementUnlockEvent;
import me.chasertw123.villagedefense.stats.Stat;
import me.chasertw123.villagedefense.stats.achievements.Achievement;

import org.bukkit.entity.Player;

public class FirstTimer extends Achievement {

    public FirstTimer(Main plugin) {
        super("default.firsttimer", "First Timer", "First Timer", "You completed your a game of Village Defense!", plugin);
    }

    @Override
    public void check(Player p) {
        if (plugin.getStatsManager().getStat(Stat.GAMESPLAYED, p) >= 1)
            plugin.getServer().getPluginManager().callEvent(new AchievementUnlockEvent(p, this));
    }

}
