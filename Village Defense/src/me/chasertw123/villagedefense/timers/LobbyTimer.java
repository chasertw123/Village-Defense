package me.chasertw123.villagedefense.timers;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.game.scoreboard.ScoreboardType;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyTimer extends BukkitRunnable {

    private Main plugin;
    private int timeLeft;

    public LobbyTimer(Main plugin) {
        this.plugin = plugin;

        timeLeft = plugin.getConfig().contains("timers.lobby") ? plugin.getConfig().getInt("timers.lobby") : 30;

        Arena a = plugin.getVoteManager().getArena(plugin);

        if (a == null)
            plugin.getGame().setArena(plugin.getArenas().get(new Random().nextInt(plugin.getArenas().size())));
        else
            plugin.getGame().setArena(a);

        for (GamePlayer gp : plugin.getGame().getPlayers()) {
            gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.LEVEL_UP, 1F, 1F);
            plugin.getScoreboardManager().giveScoreboard(gp.getPlayer(), ScoreboardType.STARTING);
        }

        this.runTaskTimer(plugin, 20L, 20L);
    }

    @Override
    public void run() {

        if (plugin.getGame().getPlayers().size() < plugin.getGame().getMinPlayers()) {

            for (GamePlayer gp : plugin.getGame().getPlayers())
                gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "Game cancelled due to lack of players!");

            this.cancel();
        }

        if (timeLeft == 0) {
            cancel();

            for (GamePlayer gp : plugin.getGame().getPlayers()) {

                if (gp.getRole() == null) {
                    gp.setRole(getRandomRole());
                    plugin.getScoreboardManager().updateScoreboard(ScoreboardType.STARTING, ScoreboardType.VOTING);
                    gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "The have recieved the " + ChatColor.BLUE + gp.getRole().getName() + ChatColor.YELLOW + " role randomly!");
                }

                //ActionBarAPI.send(gp.getPlayer(), plugin.getPrefix() + ChatColor.YELLOW + "The game has started.");
                gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.ENDERDRAGON_GROWL, 1F, 1F);

            }

            plugin.getGame().setTimer(new GameTimer(plugin));

            return;
        }

        //for (GamePlayer gp : plugin.getGame().getPlayers())
        //ActionBarAPI.send(gp.getPlayer(), plugin.getPrefix() + ChatColor.YELLOW + "The game will start in " + ChatColor.BLUE + timeLeft + ChatColor.YELLOW + " second" + ((timeLeft == 1) ? "." : "s."));

        plugin.getScoreboardManager().updateScoreboard(ScoreboardType.STARTING);

        timeLeft--;

    }

    public int getTime() {
        return timeLeft;
    }

    private Role getRandomRole() {

        Random r = new Random();
        Iterator<Entry<Class<? extends Role>, EntityType>> role = Role.roleClasses.entrySet().iterator();

        for (int i = 0; i < (r.nextInt(Role.roleClasses.size()) - 1); i++)
            role.next();

        try {
            return role.next().getKey().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
        }

        return null;
    }
}
