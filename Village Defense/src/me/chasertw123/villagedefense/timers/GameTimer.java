package me.chasertw123.villagedefense.timers;

import java.util.HashMap;
import java.util.Random;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.events.GameStartEvent;
import me.chasertw123.villagedefense.exceptions.InvalidEnemySpawnExcpetion;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.game.tools.ToolType;
import me.chasertw123.villagedefense.game.wave.Wave;
import me.chasertw123.villagedefense.utils.FancyItemStack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer extends BukkitRunnable {

    private Main plugin;

    private int wave = 1, difficulty, secondsTillNextWave = -1;
    private Random r = new Random();

    public GameTimer(Main plugin) {
        difficulty = (plugin.getGame().getPlayers().size() * 12) + 5;
        this.plugin = plugin;

        plugin.getGame().setGameState(GameState.INGAME);
        plugin.getServer().getPluginManager().callEvent(new GameStartEvent(plugin.getGame()));

        HashMap<ToolType, Integer> toolTiers = new HashMap<>();

        for (ToolType type : ToolType.values())
            toolTiers.put(type, 1);

        for (GamePlayer gp : plugin.getGame().getPlayers()) {
            if (gp.needsArrow())
                gp.getPlayer().getInventory().setItem(9, new FancyItemStack(Material.ARROW, " "));

            gp.getPlayer().getInventory().setItem(1, gp.getRole().getPrimaryAbility().getItemStack());
            gp.getPlayer().getInventory().setItem(2, gp.getRole().getSecondaryAbility().getItemStack());
            gp.getPlayer().getInventory().setItem(3, gp.getRole().getTertiaryAbility().getItemStack());
            gp.getPlayer().getInventory().setItem(4, gp.getRole().getUltraAbility().getItemStack());

            gp.setToolTiers(toolTiers);
            gp.getPlayer().teleport(plugin.getGame().getArena().getRandomLocation());

            gp.setMana(gp.getMaxMana());
        }

        playWave(new Wave(wave, difficulty, false, plugin));

        this.runTaskTimer(plugin, 20L, 20L);
    }

    @Override
    public void run() {

        int regen = r.nextInt(5) + 3;

        for (GamePlayer gp : plugin.getGame().getPlayers()) {

            if (gp.getMana() + regen > gp.getMaxMana()) {

                if (gp.getMana() < gp.getMaxMana())
                    gp.setMana(gp.getMaxMana());
            }

            else
                gp.setMana(gp.getMana() + regen);

            gp.getRole().getPrimaryAbility().decrementCooldown();
            gp.getRole().getSecondaryAbility().decrementCooldown();
            gp.getRole().getTertiaryAbility().decrementCooldown();
            gp.getRole().getUltraAbility().decrementCooldown();
        }

        if (plugin.getGame().getWave().getProgress() < 0.1) {

            if (secondsTillNextWave == -1) {
                secondsTillNextWave = plugin.getConfig().contains("timers.between-waves") ? plugin.getConfig().getInt("timers.between-waves") : 15;

                //for (GamePlayer gp : plugin.getGame().getPlayers())
                //ActionBarAPI.send(gp.getPlayer(), plugin.getPrefix() + ChatColor.YELLOW + "Next wave will start in " + ChatColor.BLUE + secondsTillNextWave + ChatColor.YELLOW + " seconds");

                return;
            }

            else if (secondsTillNextWave == 0) {

                secondsTillNextWave = -1;
                difficulty = difficulty + r.nextInt(15) + (5 * plugin.getGame().getPlayers().size());
                ++wave;

                playWave(new Wave(wave, difficulty, wave % 5 == 0, plugin));

                return;
            }

            //else if (secondsTillNextWave == 10 || secondsTillNextWave <= 5)
            //for (GamePlayer gp : plugin.getGame().getPlayers())
            //ActionBarAPI.send(gp.getPlayer(), plugin.getPrefix() + ChatColor.YELLOW + "Next wave will start in " + ChatColor.BLUE + secondsTillNextWave + ChatColor.YELLOW + " second" + ((secondsTillNextWave == 1) ? "" : "s"));

            secondsTillNextWave--;
        }
    }

    private void playWave(Wave wave) {

        plugin.getGame().setWave(wave);
        Bukkit.broadcastMessage(wave.getDifficulty() + "");

        try {
            wave.startWave();
        } catch (InvalidEnemySpawnExcpetion e) {
            e.printStackTrace();
        }

        //Title title = new Title(ChatColor.BLUE + "Wave " + wave.getWaveNumber(), "", 3, 5, 3);

        //if (wave.isBossWave())
        //    title = new Title(ChatColor.BLUE + "Boss Wave", ChatColor.YELLOW + "(Wave " + wave.getWaveNumber() + ")", 3, 5, 3);

        //for (GamePlayer gp : plugin.getGame().getPlayers())
        //    title.send(gp.getPlayer());

    }
}
