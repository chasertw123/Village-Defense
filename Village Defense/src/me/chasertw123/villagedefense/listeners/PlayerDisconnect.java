package me.chasertw123.villagedefense.listeners;

import java.util.ArrayList;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.scoreboard.ScoreboardType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnect implements Listener {

    private Main plugin;

    public PlayerDisconnect(Main plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleLeave(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        handleLeave(event.getPlayer());
    }

    @SuppressWarnings("unchecked")
    private void handleLeave(Player p) {

        plugin.getVoteManager().votes.remove(p);
        plugin.getScoreboardManager().updateScoreboard(ScoreboardType.VOTING);
        plugin.getStatsManager().saveStats(p);

        for (GamePlayer gp : (ArrayList<GamePlayer>) plugin.getGame().getPlayers().clone())
            if (gp.getPlayer().equals(p))
                plugin.getGame().getPlayers().remove(gp);
    }
}
