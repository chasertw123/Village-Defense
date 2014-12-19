package me.chasertw123.villagedefense.timers;

import me.chasertw123.villagedefense.Main;

import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

    @SuppressWarnings("unused")
    private Main plugin;

    @SuppressWarnings("unused")
    private int wave, difficulty;

    public GameTimer(Main plugin) {
        this.plugin = plugin;

        this.runTaskTimer(plugin, 20L, 20L);
    }

    @Override
    public void run() {

    }
}
