package me.chasertw123.villagedefense.timers;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.utils.ActionBarAPI;

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

        for (GamePlayer gp : plugin.getGame().getPlayers())
            gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 1);

        this.runTaskTimer(plugin, 20L, 20L);
    }

    @Override
    public void run() {

        if (timeLeft == 0) {
            cancel(); // TODO: move to next phase

            for (GamePlayer gp : plugin.getGame().getPlayers()) {

                if (gp.getRole() == null)
                    gp.setRole(getRandomRole());

                ActionBarAPI.send(gp.getPlayer(), plugin.getPrefix() + ChatColor.YELLOW + "The game has started.");
                gp.getPlayer().playSound(gp.getPlayer().getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
            }

            return;
        }

        for (GamePlayer gp : plugin.getGame().getPlayers())
            ActionBarAPI.send(gp.getPlayer(), plugin.getPrefix() + ChatColor.YELLOW + "The game will start in " + ChatColor.AQUA + timeLeft + ChatColor.YELLOW + " second" + ((timeLeft == 1) ? "." : "s."));

        timeLeft--;

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
