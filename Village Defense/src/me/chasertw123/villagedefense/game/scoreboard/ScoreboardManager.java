package me.chasertw123.villagedefense.game.scoreboard;

import java.util.HashMap;
import java.util.Map.Entry;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.arena.VoteManager.ArenaCounter;
import me.chasertw123.villagedefense.timers.LobbyTimer;

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

        int score = 15;

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective(p.getName(), "dummy");

        Game game = plugin.getGame();
        GamePlayer gp = game.getGamePlayer(p);

        obj.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Village Defense");

        switch (st) {

            case VOTING:

                obj.getScore(ChatColor.AQUA + "").setScore(score--);
                obj.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Players").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + game.getPlayers().size() + "/" + game.getMaxPlayers()).setScore(score--);
                obj.getScore(ChatColor.BLUE + "").setScore(score--);
                obj.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Maps").setScore(score--);

                int maps = 0;
                for (Entry<Arena, ArenaCounter> entry : plugin.getVoteManager().getArenaCounter(plugin).entrySet()) {

                    if (maps == 3)
                        break;

                    obj.getScore(ChatColor.WHITE + "" + entry.getKey().getName() + " - " + entry.getValue().getCount()).setScore(score--);
                    maps++;
                }

                obj.getScore(ChatColor.GRAY + "").setScore(score--);
                obj.getScore(ChatColor.YELLOW + "" + ChatColor.BOLD + "Role").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + (gp.getRole() != null ? gp.getRole().getName() : "Role Not Selected")).setScore(score--);
                obj.getScore(ChatColor.GOLD + "").setScore(score--);
                obj.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Vote").setScore(score--);
                obj.getScore(ChatColor.WHITE + "/vd vote <Arena>").setScore(score--);
                break;

            case STARTING:

                if (!(plugin.getGame().getTimer() instanceof LobbyTimer))
                    return;

                obj.getScore(ChatColor.AQUA + "").setScore(score--);
                obj.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Players").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + game.getPlayers().size() + "/" + game.getMaxPlayers()).setScore(score--);
                obj.getScore(ChatColor.BLUE + "").setScore(score--);
                obj.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Countdown").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + ((LobbyTimer) plugin.getGame().getTimer()).getTime()).setScore(score--);
                obj.getScore(ChatColor.BOLD + "").setScore(score--);
                obj.getScore(ChatColor.YELLOW + "" + ChatColor.BOLD + "Role").setScore(score--);
                obj.getScore(ChatColor.WHITE + (gp.getRole() != null ? gp.getRole().getName() : "Role Not Selected")).setScore(score--);
                obj.getScore(ChatColor.GOLD + "").setScore(score--);
                obj.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Map").setScore(score--);
                obj.getScore(ChatColor.WHITE + plugin.getGame().getArena().getName()).setScore(score--);
                break;

            default:
                break;
        }

        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        p.setScoreboard(board);

        players.put(p, st);
    }

    public void updateScoreboard(ScoreboardType... sts) {
        for (ScoreboardType st : sts)
            for (Player p : players.keySet())
                if (players.get(p) == st)
                    this.giveScoreboard(p, st);
    }
}