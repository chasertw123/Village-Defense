package me.chasertw123.villagedefense.game.scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.arena.VoteManager.ArenaCounter;
import me.chasertw123.villagedefense.timers.GameTimer;
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
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective(p.getName(), "dummy");

        int score = 15;
        Game game = plugin.getGame();
        GamePlayer gp = game.getGamePlayer(p);

        obj.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "» Village Defense «");

        switch (st) {

            case VOTING:

                obj.getScore(ChatColor.AQUA + "").setScore(score--);
                obj.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Players:").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + game.getPlayers().size() + "/" + game.getMaxPlayers()).setScore(score--);
                obj.getScore(ChatColor.BLUE + "").setScore(score--);
                obj.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Maps:").setScore(score--);

                int maps = 0;
                ArrayList<Arena> usedArenas = new ArrayList<Arena>();
                for (Entry<Arena, ArenaCounter> entry : plugin.getVoteManager().getArenaCounter(plugin).entrySet()) {
                    if (maps == 3)
                        break;

                    if (usedArenas.contains(entry.getKey()))
                        continue;

                    obj.getScore(ChatColor.WHITE + "" + entry.getKey().getName() + " - " + entry.getValue().getCount()).setScore(score--);
                    usedArenas.add(entry.getKey());
                    maps++;
                }

                obj.getScore(ChatColor.GRAY + "").setScore(score--);
                obj.getScore(ChatColor.YELLOW + "" + ChatColor.BOLD + "Selected Class:").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + (gp.getRole() != null ? gp.getRole().getName() : "Role Not Selected")).setScore(score--);
                obj.getScore(ChatColor.GOLD + "").setScore(score--);
                obj.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Vote:").setScore(score--);

                if (plugin.getVoteManager().votes.containsKey(p))
                    obj.getScore(ChatColor.WHITE + "You voted for " + plugin.getVoteManager().votes.get(p).getName() + "!").setScore(score--);
                else
                    obj.getScore(ChatColor.WHITE + "/vd vote <Arena>").setScore(score--);
                break;

            case STARTING:

                if (!(game.getTimer() instanceof LobbyTimer))
                    return;

                obj.getScore(ChatColor.AQUA + "").setScore(score--);
                obj.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Players:").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + game.getPlayers().size() + "/" + game.getMaxPlayers()).setScore(score--);
                obj.getScore(ChatColor.BLUE + "").setScore(score--);
                obj.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Countdown:").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + ((LobbyTimer) game.getTimer()).getTime() + "s").setScore(score--);
                obj.getScore(ChatColor.BOLD + "").setScore(score--);
                obj.getScore(ChatColor.YELLOW + "" + ChatColor.BOLD + "Selected Class:").setScore(score--);
                obj.getScore(ChatColor.WHITE + (gp.getRole() != null ? gp.getRole().getName() : "Role Not Selected")).setScore(score--);
                obj.getScore(ChatColor.GOLD + "").setScore(score--);
                obj.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Map:").setScore(score--);
                obj.getScore(ChatColor.WHITE + game.getArena().getName()).setScore(score--);
                break;

            case INGAME:

                if (!(game.getTimer() instanceof GameTimer))
                    return;

                obj.getScore(ChatColor.AQUA + "").setScore(score--);
                obj.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + "Gold:").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + gp.getGold()).setScore(score--);
                obj.getScore(ChatColor.BLUE + "").setScore(score--);
                obj.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + "Wave:").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + ((GameTimer) game.getTimer()).getWave()).setScore(score--);
                obj.getScore(ChatColor.BOLD + "").setScore(score--);
                obj.getScore(ChatColor.YELLOW + "" + ChatColor.BOLD + "Enemies Left:").setScore(score--);
                obj.getScore(ChatColor.WHITE + "" + game.getWave().getEnemiesLeft()).setScore(score--);
                obj.getScore(ChatColor.GOLD + "").setScore(score--);
                obj.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Map:").setScore(score--);
                obj.getScore(ChatColor.WHITE + game.getArena().getName()).setScore(score--);

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