package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GameState;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerLogin implements Listener {

    private Main plugin;

    public PlayerLogin(Main plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {

        if (plugin.getGame().getGameState() == GameState.STARTINGUP)
            event.disallow(Result.KICK_OTHER, "Village Defense is currently starting up.");

        else if (plugin.getGame().getGameState() == GameState.RESTARTING)
            event.disallow(Result.KICK_OTHER, "Village Defense is currently restarting.");

        else if (plugin.getGame().getGameState() == GameState.STARTING || plugin.getGame().getGameState() == GameState.INGAME || plugin.getGame().getGameState() == GameState.ENDING)
            event.disallow(Result.KICK_OTHER, "Village Defense is currently restarting.");

    }
}
