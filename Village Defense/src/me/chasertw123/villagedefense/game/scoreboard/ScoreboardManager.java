package me.chasertw123.villagedefense.game.scoreboard;

import java.util.HashMap;
import java.util.Map.Entry;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.arena.VoteManager.ArenaCounter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    private Main plugin;

    private HashMap<Player, ScoreboardType> players = new HashMap<Player, ScoreboardType>();

    public ScoreboardManager(Main plugin) {
        this.plugin = plugin;
    }

    public void giveScoreboard(Player p, ScoreboardType st) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective(p.getName(), "dummy");
        Game game = plugin.getGame();

        obj.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Villager Defense");

        String pre = "" + ChatColor.BLUE;
        String spec = "" + ChatColor.GOLD;

        switch (st) {
            case VOTING:
                int score = 9;
                obj.getScore(ChatColor.AQUA + "").setScore(score--);
                obj.getScore(pre + "Players: " + spec + game.getPlayers().size() + pre + "/" + spec + game.getMaxPlayers()).setScore(score--);
                obj.getScore(ChatColor.BLUE + "").setScore(score--);
                obj.getScore(pre + "Map votes").setScore(score--);
                obj.getScore(ChatColor.BOLD + "").setScore(score--);

                for (Entry<Arena, ArenaCounter> entry : plugin.getVoteManager().getArenaCounter(plugin).entrySet()) {
                    if (score == 2)
                        break;
                    obj.getScore(pre + entry.getKey().getName() + ":" + spec + entry.getValue().getCount()).setScore(score--);
                }

                obj.getScore(ChatColor.GRAY + "").setScore(score--);
                obj.getScore(pre + "/vd vote <Arena>").setScore(score--);
                break;

            default:
                break;
        }

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        p.setScoreboard(board);

        players.put(p, st);
    }

    public void updateScoreboard(ScoreboardType st) {
        for (Player p : players.keySet())
            if (players.get(p) == st)
                this.giveScoreboard(p, st);
    }
}