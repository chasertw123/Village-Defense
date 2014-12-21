package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.events.GameStartEvent;
import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStart implements Listener {

    private Main plugin;

    public GameStart(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {

        for (GamePlayer gp : plugin.getGame().getPlayers())
            if (gp.getRole().getName().equalsIgnoreCase("Archer"))
                gp.setNeedsArrow(true);
    }
}
