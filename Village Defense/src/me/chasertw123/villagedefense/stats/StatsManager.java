package me.chasertw123.villagedefense.stats;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import me.chasertw123.villagedefense.Main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class StatsManager {

    private Main plugin;
    private FileConfiguration statsyml;
    private File statsFile;

    private HashMap<UUID, Integer[]> stats = new HashMap<UUID, Integer[]>();

    private HashMap<UUID, Integer> mobKills = new HashMap<UUID, Integer>();
    private HashMap<UUID, Integer> deaths = new HashMap<UUID, Integer>();
    private HashMap<UUID, Integer> gamesPlayed = new HashMap<UUID, Integer>();
    private HashMap<UUID, Integer> wavesPlayed = new HashMap<UUID, Integer>();
    private HashMap<UUID, Integer> wavesWon = new HashMap<UUID, Integer>();
    private HashMap<UUID, Integer> wavesLost = new HashMap<UUID, Integer>();
    private HashMap<UUID, Integer> totalGoldEarned = new HashMap<UUID, Integer>();
    private HashMap<UUID, Integer> totalGoldSpent = new HashMap<UUID, Integer>();

    public StatsManager() {
        statsFile = new File(plugin.getDataFolder().getAbsoluteFile() + File.separator + "stats.yml");

        try {
            statsFile.createNewFile();
        } catch (IOException e) {
            plugin.sendConsoleSevere("Failed to generate stats.yml!");
        }

        statsyml = YamlConfiguration.loadConfiguration(statsFile);

        if (!plugin.usesSQL())
            for (Player p : Bukkit.getOnlinePlayers())
                this.addStatsToMap(p);

        else {

            // TODO: Setup SQL Database

        }
    }

    public int getStat(Stat stat, Player p) {

        int amount = 0;

        switch (stat) {

            case MOBKILLS:
                amount = mobKills.get(p.getUniqueId());
                break;

            case DEATHS:
                amount = deaths.get(p.getUniqueId());
                break;

            case GAMESPLAYED:
                amount = gamesPlayed.get(p.getUniqueId());
                break;

            case WAVESPLAYED:
                amount = wavesPlayed.get(p.getUniqueId());
                break;

            case WAVESWON:
                amount = wavesWon.get(p.getUniqueId());
                break;

            case WAVESLOST:
                amount = wavesLost.get(p.getUniqueId());
                break;

            case TOTALGOLDEARNED:
                amount = totalGoldEarned.get(p.getUniqueId());
                break;

            case TOTALGOLDSPENT:
                amount = totalGoldSpent.get(p.getUniqueId());
                break;

            default:
                break;
        }

        return amount;
    }

    public void addStatsToMap(Player p) {

        if (!plugin.usesSQL()) {

            FileConfiguration config = plugin.getConfig();

            for (String key : config.getConfigurationSection(p.getUniqueId().toString()).getKeys(false))
                stats.put(p.getUniqueId(), new Integer[] { config.getInt(key + Stat.MOBKILLS.toString().toLowerCase()), config.getInt(key + Stat.DEATHS.toString().toLowerCase()), config.getInt(key + Stat.GAMESPLAYED.toString().toLowerCase()), config.getInt(key + Stat.WAVESPLAYED.toString().toLowerCase()), config.getInt(key + Stat.WAVESWON.toString().toLowerCase()), config.getInt(key + Stat.WAVESLOST.toString().toLowerCase()), config.getInt(key + Stat.TOTALGOLDEARNED.toString().toLowerCase()), config.getInt(key + Stat.TOTALGOLDSPENT.toString().toLowerCase()) });
        }

        else {

            // TODO: Add SQL stats to map

        }
    }

    public void saveStats(Player p) {

        if (!plugin.usesSQL()) {
            for (Stat stat : Stat.values())
                plugin.getConfig().set(p.getUniqueId().toString() + stat.toString().toLowerCase(), stat.ordinal());

            try {
                statsyml.save(statsFile);
            } catch (IOException e) {
                plugin.sendConsoleSevere("Failed to save stats.yml!");
            }
        }

        else {

            // TODO: Save to SQL Database

        }
    }
}
