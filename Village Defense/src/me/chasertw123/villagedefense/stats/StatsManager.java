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
import me.chasertw123.villagedefense.stats.achievements.Achievement;
import me.chasertw123.villagedefense.stats.achievements.Achievements;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class StatsManager {

    private Main plugin;

    // FlatFile
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
                String sql = "CREATE TABLE IF NOT EXISTS villagedefense (uuid CHAR(36) not NULL, mobkills INT(64), deaths INT(64), gamesplayed INT(64), wavesplayed INT(64), waveswon INT(64), waveslost INT(64), totalgoldearned INT(64), totalgoldspent INT(64), PRIMARY KEY (uuid))";

                stmt.executeUpdate(sql);
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `villagedefense_achievements` (uuid CHAR(36) not NULL, PRIMARY KEY (uuid))");

                stmt.close();

            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.closeConnection();
            }

        }

        for (Player p : Bukkit.getOnlinePlayers())
            this.loadStats(p);
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

    public void addAchievement(String id) {

        if (plugin.usesSQL()) {

            this.openConnection();

            try {
                System.out.println("Registering achievement " + id); // TODO; Remove debug line
                c.createStatement().executeUpdate("ALTER TABLE `villagedefense_achievements` ADD " + id + " TINYINT(1)");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this.closeConnection();
            }
        }
    }

    public void setAchievement(Achievement a, Player p, boolean has) {

        stats.get(p.getUniqueId()).getAchievements().put(a.getId(), has);
        if (!plugin.usesSQL()) {
            statsyml.set(p.getUniqueId() + ".achievements." + a.getId(), has);

            try {
                statsyml.save(statsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else {

            this.openConnection();

            try {
                c.createStatement().executeUpdate("UPDATE `villagedefense_achievements` SET " + a.getId() + "=" + (has ? 1 : 0) + " WHERE uuid=" + p.getUniqueId().toString());
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this.closeConnection();
            }
        }
    }

    public boolean getAchievement(Achievement a, Player p) {
        if (!stats.containsKey(p.getUniqueId()) || !stats.get(p.getUniqueId()).getAchievements().containsKey(a.getId()))
            loadStats(p);

        return stats.get(p.getUniqueId()).getAchievements().get(a.getId());
    }

    public HashMap<String, Boolean> getAchievements(Player p) {
        HashMap<String, Boolean> achievements = new HashMap<>();

        if (plugin.usesSQL()) {

            this.openConnection();

            try {
                PreparedStatement current = c.prepareStatement("SELECT * FROM `villagedefense_achievements` WHERE uuid=?;");

                current.setString(1, p.getName());

                ResultSet result = current.executeQuery();
                result.next();

                for (Achievement a : Achievements.ID_MAP.values())
                    achievements.put(a.getId(), result.getBoolean(a.getId()));

                current.close();
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                this.closeConnection();
            }
        }

        else {
            for (Achievement a : Achievements.ID_MAP.values()) {
                if (statsyml.contains(p.getUniqueId() + ".achievements." + a.getId()))
                    achievements.put(a.getId(), statsyml.getBoolean(p.getUniqueId() + ".achievements." + a.getId()));
                else
                    achievements.put(a.getId(), false);
            }
        }

        return achievements;
    }

    public void loadStats(Player p) {

        if (!plugin.usesSQL()) {

            if (!statsyml.contains(p.getUniqueId().toString())) {
                stats.put(p.getUniqueId(), new PlayerStats(0, 0, 0, 0, 0, 0, 0, 0, new HashMap<String, Boolean>()));
                this.saveStats(p);
                return;
            }

            for (String key : statsyml.getConfigurationSection(p.getUniqueId().toString()).getKeys(false))
                stats.put(p.getUniqueId(), new PlayerStats(statsyml.getInt(key + "." + Stat.MOBKILLS.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.DEATHS.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.GAMESPLAYED.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.WAVESPLAYED.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.WAVESWON.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.WAVESLOST.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.TOTALGOLDEARNED.toString().toLowerCase()), statsyml.getInt(key + "." + Stat.TOTALGOLDSPENT.toString().toLowerCase()), getAchievements(p)));
        }

        else {

            if (!this.containsPlayer(p)) {
                stats.put(p.getUniqueId(), new PlayerStats(0, 0, 0, 0, 0, 0, 0, 0, new HashMap<String, Boolean>()));
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

            stats.put(p.getUniqueId(), new PlayerStats(s2[0], s2[1], s2[2], s2[3], s2[4], s2[5], s2[6], s2[7], getAchievements(p)));
        }
    }

    public void saveStats(Player p) {

        if (!plugin.usesSQL()) {

            for (Stat stat : Stat.values())
                statsyml.set(p.getUniqueId().toString() + "." + stat.toString().toLowerCase(), this.getStat(stat, p));

            for (Achievement a : Achievements.ID_MAP.values())
                statsyml.set(p.getUniqueId() + ".achievements." + a.getId(), stats.get(p.getUniqueId()).getAchievements().get(a.getId()));

            try {
                statsyml.save(statsFile);
            } catch (IOException e) {
                plugin.sendConsoleSevere("Failed to save stats.yml!");
            }

            return;
        }

        else {

            this.openConnection();

            if (!this.containsPlayer(p))
                return;

            try {

                String sets = "";
                for (Stat stat : Stat.values()) {
                    if (sets.equals(""))
                        sets = stat.toString().toLowerCase() + "=" + this.getStat(stat, p);
                    else
                        sets += ", " + stat.toString().toLowerCase() + "=" + this.getStat(stat, p);
                }

                PreparedStatement stmt1 = c.prepareStatement("UPDATE `villagedefense` SET " + sets + " WHERE uuid=?");

                stmt1.setString(1, p.getUniqueId().toString());
                stmt1.executeUpdate();

                String achievementSets = "";
                for (Achievement a : Achievements.ID_MAP.values())
                    if (achievementSets.equals(""))
                        achievementSets = a.getId() + "=" + (this.getAchievement(a, p) ? 1 : 0);
                    else
                        achievementSets += ", " + a.getId() + "=" + (this.getAchievement(a, p) ? 1 : 0);

                PreparedStatement stmt2 = c.prepareStatement("UPDATE `villagedefense_achievements` SET " + achievementSets + " WHERE uuid=?");

                stmt2.setString(1, p.getUniqueId().toString());
                stmt2.executeUpdate();

                stmt1.close();
                stmt2.close();
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
