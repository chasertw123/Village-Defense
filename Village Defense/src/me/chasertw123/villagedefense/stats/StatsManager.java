package me.chasertw123.villagedefense.stats;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import me.chasertw123.villagedefense.Main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class StatsManager {

    private Main plugin;

    // FlatFIile
    private FileConfiguration statsyml;
    private File statsFile;

    // MYSQL
    private Connection c;

    private HashMap<UUID, PlayerStats> stats = new HashMap<UUID, PlayerStats>();

    public StatsManager(Main plugin) {

        this.plugin = plugin;

        if (!plugin.usesSQL()) {

            statsFile = new File(plugin.getDataFolder().getAbsoluteFile() + File.separator + "stats.yml");

            try {
                statsFile.createNewFile();
            } catch (IOException e) {
                plugin.sendConsoleSevere("Failed to generate stats.yml!");
            }

            statsyml = YamlConfiguration.loadConfiguration(statsFile);
        }

        else {

            this.openConnection();

            if (c == null)
                return;

            try {

                Class.forName("com.mysql.jdbc.Driver");

                Statement stmt = c.createStatement();
                String sql = "CREATE TABLE IF NOT EXISTS villagedefense (uuid VARCHAR(36) not NULL, mobkills INT(64), deaths INT(64), gamesplayed INT(64), wavesplayed INT(64), waveswon INT(64), waveslost INT(64), totalgoldearned INT(64), totalgoldspent INT(64), PRIMARY KEY (uuid))";

                stmt.executeUpdate(sql);

            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.closeConnection();
            }

        }

        for (Player p : Bukkit.getOnlinePlayers())
            this.addStatsToMap(p);
    }

    public int getStat(Stat stat, Player p) {
        return stats.get(p.getUniqueId()).getStat(stat);
    }

    public void setStat(Stat stat, int i, Player p) {

        PlayerStats ps = stats.get(p.getUniqueId());
        ps.setStat(stat, i);
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
                stats.put(p.getUniqueId(), new PlayerStats(0, 0, 0, 0, 0, 0, 0, 0));
                this.saveStats(p);
                return;
            }

            for (String key : statsyml.getConfigurationSection(p.getUniqueId().toString()).getKeys(false))
                stats.put(p.getUniqueId(), new PlayerStats(statsyml.getInt(key + "." + Stat.MOBKILLS.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.DEATHS.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.GAMESPLAYED.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.WAVESPLAYED.toString().toLowerCase()), statsyml.getInt(key + "."
                    + Stat.WAVESWON.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.WAVESLOST.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.TOTALGOLDEARNED.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.TOTALGOLDSPENT.toString().toLowerCase())));
        }

        else {

            if (!this.containsPlayer(p)) {
                stats.put(p.getUniqueId(), new PlayerStats(0, 0, 0, 0, 0, 0, 0, 0));
                this.saveStats(p);
                return;
            }

            ArrayList<Integer> s = new ArrayList<Integer>();

            this.openConnection();

            try {

                for (Stat stat : Stat.values()) {

                    PreparedStatement current = c.prepareStatement("SELECT " + stat.toString().toLowerCase() + " FROM `villagedefense` WHERE uuid=?;");

                    current.setString(1, p.getName());

                    ResultSet result = current.executeQuery();
                    result.next();

                    s.add(result.getInt(stat.toString().toLowerCase()));

                    current.close();
                    result.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this.closeConnection();
            }

            Integer[] s2 = new Integer[s.size()];
            Iterator<Integer> iterator = s.iterator();

            for (int i = 0; i < s2.length; i++)
                s2[i] = iterator.next().intValue();

            stats.put(p.getUniqueId(), new PlayerStats(s2[0], s2[1], s2[2], s2[3], s2[4], s2[5], s2[6], s2[7]));
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

            this.openConnection();

            if (!this.containsPlayer(p))
                return;

            try {

                for (Stat stat : Stat.values()) {

                    PreparedStatement stmt = c.prepareStatement("UPDATE `villagedefense` SET " + stat.toString().toLowerCase() + "=? WHERE uuid=?");

                    stmt.setInt(1, this.getStat(stat, p));
                    stmt.setString(2, p.getUniqueId().toString());
                    stmt.executeUpdate();
                    stmt.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this.closeConnection();
            }
        }
    }

    public synchronized void openConnection() {

        FileConfiguration config = plugin.getConfig();

        try {
            c = DriverManager.getConnection("jdbc:mysql://" + config.getString("sql.ip") + ":" + config.getString("sql.port") + "/" + config.getString("sql.database"), config.getString("sql.username"), config.getString("sql.password"));
        } catch (Exception e) {
            plugin.sendConsoleSevere("Failed to open the connection to the MySQL server!");
        }
    }

    public synchronized void closeConnection() {

        try {
            c.close();
        } catch (Exception e) {
            plugin.sendConsoleSevere("Failed to close the connection to the MySQL server!");
        }
    }

    public synchronized boolean containsPlayer(Player p) {

        this.openConnection();

        try {

            PreparedStatement sql = c.prepareStatement("SELECT * FROM `villagedefense` WHERE uuid=?;");
            sql.setString(1, p.getUniqueId().toString());

            ResultSet result = sql.executeQuery();
            boolean hasPlayer = result.next();

            sql.close();
            result.close();

            return hasPlayer;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            this.closeConnection();
        }
    }
}
