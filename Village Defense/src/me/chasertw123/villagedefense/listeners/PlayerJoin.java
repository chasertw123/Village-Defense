package me.chasertw123.villagedefense.listeners;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.game.scoreboard.ScoreboardType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private Main plugin;

    public PlayerJoin(Main plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.setJoinMessage("");

        plugin.getStatsManager().loadStats(event.getPlayer());
        plugin.getGame().getPlayers().add(new GamePlayer(null, event.getPlayer()));

        if (plugin.getGame().getPlayers().size() >= plugin.getGame().getMinPlayers() && plugin.getGame().getGameState() == GameState.LOBBY)
            plugin.getGame().startGame(plugin);

        else if (plugin.getGame().getGameState() == GameState.DISABLED)
            Bukkit.broadcastMessage(plugin.getPrefix() + ChatColor.RED + "**Warning the game has not been set up yet** ");

        else if (plugin.getGame().getPlayers().size() < plugin.getGame().getMinPlayers()) {
            int amount = plugin.getGame().getMinPlayers() - plugin.getGame().getPlayers().size();
            Bukkit.broadcastMessage(plugin.getPrefix() + ChatColor.YELLOW + "We need " + ChatColor.BLUE + amount + ChatColor.YELLOW + " more player" + ((amount == 1) ? "" : "s") + " to join!");
        }

        if (plugin.getGame().getGameState() == GameState.LOBBY)
            plugin.getScoreboardManager().giveScoreboard(event.getPlayer(), ScoreboardType.VOTING);

        else if (plugin.getGame().getGameState() == GameState.STARTING)
            plugin.getScoreboardManager().giveScoreboard(event.getPlayer(), ScoreboardType.STARTING);

    }
}
