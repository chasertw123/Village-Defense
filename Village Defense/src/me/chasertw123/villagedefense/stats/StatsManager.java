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

    public StatsManager() {

        if (!plugin.usesSQL()) {

            statsFile = new File(plugin.getDataFolder().getAbsoluteFile() + File.separator + "stats.yml");

            try {
                statsFile.createNewFile();
            } catch (IOException e) {
                plugin.sendConsoleSevere("Failed to generate stats.yml!");
            }

            statsyml = YamlConfiguration.loadConfiguration(statsFile);

            for (Player p : Bukkit.getOnlinePlayers())
                this.addStatsToMap(p);
        }

        else {

            // TODO: Setup SQL Database

        }
    }

    public int getStat(Stat stat, Player p) {
        return stats.get(p.getUniqueId())[stat.ordinal()];
    }

    public void setStat(Stat stat, int i, Player p) {

        switch (stat) {

            case MOBKILLS:
                stats.put(p.getUniqueId(), new Integer[] { i, getStat(Stat.DEATHS, p), getStat(Stat.GAMESPLAYED, p), getStat(Stat.WAVESPLAYED, p), getStat(Stat.WAVESWON, p), getStat(Stat.WAVESLOST, p), getStat(Stat.TOTALGOLDEARNED, p), getStat(Stat.TOTALGOLDSPENT, p) });
                break;

            case DEATHS:
                stats.put(p.getUniqueId(), new Integer[] { getStat(Stat.MOBKILLS, p), i, getStat(Stat.GAMESPLAYED, p), getStat(Stat.WAVESPLAYED, p), getStat(Stat.WAVESWON, p), getStat(Stat.WAVESLOST, p), getStat(Stat.TOTALGOLDEARNED, p), getStat(Stat.TOTALGOLDSPENT, p) });
                break;

            case GAMESPLAYED:
                stats.put(p.getUniqueId(), new Integer[] { getStat(Stat.MOBKILLS, p), getStat(Stat.DEATHS, p), i, getStat(Stat.WAVESPLAYED, p), getStat(Stat.WAVESWON, p), getStat(Stat.WAVESLOST, p), getStat(Stat.TOTALGOLDEARNED, p), getStat(Stat.TOTALGOLDSPENT, p) });
                break;

            case WAVESPLAYED:
                stats.put(p.getUniqueId(), new Integer[] { getStat(Stat.MOBKILLS, p), getStat(Stat.DEATHS, p), getStat(Stat.GAMESPLAYED, p), i, getStat(Stat.WAVESWON, p), getStat(Stat.WAVESLOST, p), getStat(Stat.TOTALGOLDEARNED, p), getStat(Stat.TOTALGOLDSPENT, p) });
                break;

            case WAVESWON:
                stats.put(p.getUniqueId(), new Integer[] { getStat(Stat.MOBKILLS, p), getStat(Stat.DEATHS, p), getStat(Stat.GAMESPLAYED, p), getStat(Stat.WAVESPLAYED, p), i, getStat(Stat.WAVESLOST, p), getStat(Stat.TOTALGOLDEARNED, p), getStat(Stat.TOTALGOLDSPENT, p) });
                break;

            case WAVESLOST:
                stats.put(p.getUniqueId(), new Integer[] { getStat(Stat.MOBKILLS, p), getStat(Stat.DEATHS, p), getStat(Stat.GAMESPLAYED, p), getStat(Stat.WAVESPLAYED, p), getStat(Stat.WAVESWON, p), i, getStat(Stat.TOTALGOLDEARNED, p), getStat(Stat.TOTALGOLDSPENT, p) });
                break;

            case TOTALGOLDEARNED:
                stats.put(p.getUniqueId(), new Integer[] { getStat(Stat.MOBKILLS, p), getStat(Stat.DEATHS, p), getStat(Stat.GAMESPLAYED, p), getStat(Stat.WAVESPLAYED, p), getStat(Stat.WAVESWON, p), getStat(Stat.WAVESLOST, p), i, getStat(Stat.TOTALGOLDSPENT, p) });
                break;

            case TOTALGOLDSPENT:
                stats.put(p.getUniqueId(), new Integer[] { getStat(Stat.MOBKILLS, p), getStat(Stat.DEATHS, p), getStat(Stat.GAMESPLAYED, p), getStat(Stat.WAVESPLAYED, p), getStat(Stat.WAVESWON, p), getStat(Stat.WAVESLOST, p), getStat(Stat.TOTALGOLDEARNED, p), i });
                break;

            default:
                break;
        }
    }

    public void incrementStat(Stat stat, int i, Player p) {
        this.setStat(stat, this.getStat(stat, p) + i, p);
    }

    public void incrementStat(Stat stat, Player p) {
        this.setStat(stat, this.getStat(stat, p) + 1, p);
    }

    public void decrementStat(Stat stat, int i, Player p) {
        this.setStat(stat, this.getStat(stat, p) - i, p);
    }

    public void decrementStat(Stat stat, Player p) {
        this.setStat(stat, this.getStat(stat, p) - 1, p);
    }

    public void addStatsToMap(Player p) {

        if (!plugin.usesSQL()) {

            if (!statsyml.contains(p.getUniqueId().toString())) {
                stats.put(p.getUniqueId(), new Integer[] { 0, 0, 0, 0, 0, 0, 0, 0 });
                this.saveStats(p);
                return;
            }

            for (String key : statsyml.getConfigurationSection(p.getUniqueId().toString()).getKeys(false))
                stats.put(p.getUniqueId(), new Integer[] { statsyml.getInt(key + "." + Stat.MOBKILLS.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.DEATHS.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.GAMESPLAYED.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.WAVESPLAYED.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.WAVESWON.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.WAVESLOST.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.TOTALGOLDEARNED.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.TOTALGOLDSPENT.toString().toLowerCase()) });
        }

        else {

            // TODO: Add SQL stats to map

        }
    }

    public void saveStats(Player p) {

        if (!plugin.usesSQL()) {
            for (Stat stat : Stat.values())
                plugin.getConfig().set(p.getUniqueId().toString() + "." + stat.toString().toLowerCase(), stat.ordinal());

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
