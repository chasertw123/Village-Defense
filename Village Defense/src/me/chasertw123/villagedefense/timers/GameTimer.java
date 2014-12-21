package me.chasertw123.villagedefense.timers;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.events.GameStartEvent;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

    @SuppressWarnings("unused")
    private Main plugin;

    @SuppressWarnings("unused")
    private int wave, difficulty;

    public GameTimer(Main plugin) {
        this.plugin = plugin;

        plugin.getGame().setGameState(GameState.INGAME);
        plugin.getServer().getPluginManager().callEvent(new GameStartEvent(plugin.getGame()));

        this.runTaskTimer(plugin, 20L, 20L);

        for (GamePlayer gp : plugin.getGame().getPlayers())
            if (gp.needsArrow())
                gp.getPlayer().getInventory().setItem(9, new FancyItemStack(Material.ARROW, " "));
    }

    @Override
    public void run() {

    }
}
