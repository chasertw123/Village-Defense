package me.chasertw123.villagedefense;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import me.chasertw123.villagedefense.commands.VillageDefenseCmd;
import me.chasertw123.villagedefense.exceptions.VillageDefenseException;
import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.GameState;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.arena.VoteManager;
import me.chasertw123.villagedefense.game.building.Building;
import me.chasertw123.villagedefense.game.enemy.Minion;
import me.chasertw123.villagedefense.game.enemy.Tank;
import me.chasertw123.villagedefense.game.enemy.boss.BabyTerror;
import me.chasertw123.villagedefense.game.role.Role;
import me.chasertw123.villagedefense.game.role.RoleSelect;
import me.chasertw123.villagedefense.game.scoreboard.ScoreboardManager;
import me.chasertw123.villagedefense.game.scoreboard.ScoreboardType;
import me.chasertw123.villagedefense.listeners.AchievementUnlock;
import me.chasertw123.villagedefense.listeners.EntityDamageByEntity;
import me.chasertw123.villagedefense.listeners.EntityTarget;
import me.chasertw123.villagedefense.listeners.GameStart;
import me.chasertw123.villagedefense.listeners.InventoryClick;
import me.chasertw123.villagedefense.listeners.PlayerDisconnect;
import me.chasertw123.villagedefense.listeners.PlayerInteract;
import me.chasertw123.villagedefense.listeners.PlayerInteractEntity;
import me.chasertw123.villagedefense.listeners.PlayerJoin;
import me.chasertw123.villagedefense.listeners.PlayerLogin;
import me.chasertw123.villagedefense.stats.StatsManager;
import me.chasertw123.villagedefense.stats.achievements.Achievements;
import me.chasertw123.villagedefense.utils.LocationUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    /* -=-=- Notes -=-=-
     **********************************************************************************
     * 
     * -=- Achievements -=-
     * 
     * Achievement Ideas
     * - Beta Tester
     * 
     * Problem: How to store achievements over MySQL/FlatFile and effective
     * 
     **********************************************************************************
     * 
     * -=- Multiple Maps -=-
     * 
     * Multiple Maps
     * Make it so players can vote on which map they play on
     * Then after a game can chose to rate it from 1 to 10
     * 
     * Problem: Game is currently designed for only one map
     * 
     **********************************************************************************/

    private final String prefix = ChatColor.WHITE + "[" + ChatColor.GREEN + "VD" + ChatColor.WHITE + "]" + ChatColor.RESET + " ";

    private FileConfiguration arenayml;
    private File arenaFile = new File(getDataFolder().getAbsoluteFile() + File.separator + "arena.yml");

    private ArrayList<Arena> arenas = new ArrayList<>();
    private Game game;

    private StatsManager statsManager;
    private VoteManager voteManager;
    private ScoreboardManager scoreboardManager;

    private boolean usesSQL;

    public void onEnable() {

        System.out.println("You are about to witness the evolution of something awesome.");

        this.saveDefaultConfig();

        usesSQL = this.getConfig().getBoolean("sql.use", false);

        statsManager = new StatsManager(this);
        scoreboardManager = new ScoreboardManager(this);

        PluginManager pm = this.getServer().getPluginManager();

        pm.registerEvents(new AchievementUnlock(this), this);
        pm.registerEvents(new EntityDamageByEntity(this), this);
        pm.registerEvents(new EntityTarget(this), this);
        pm.registerEvents(new GameStart(this), this);
        pm.registerEvents(new InventoryClick(this), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new PlayerInteractEntity(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerDisconnect(this), this);
        pm.registerEvents(new PlayerLogin(this), this);

        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingArmorsmith.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingBrewery.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingChurch.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingTownhall.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingWeaponsmith.class);
        Building.registerBuilding(me.chasertw123.villagedefense.game.building.BuildingFarmer.class);

        if (!this.getConfig().getBoolean("disabled-roles.archer"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Archer.class, EntityType.SKELETON);

        if (!this.getConfig().getBoolean("disabled-roles.assassin"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Assassin.class, EntityType.CREEPER);

        if (!this.getConfig().getBoolean("disabled-roles.healer"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Healer.class, EntityType.SLIME);

        if (!this.getConfig().getBoolean("disabled-roles.mage"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Mage.class, EntityType.WITCH);

        if (!this.getConfig().getBoolean("disabled-roles.tank"))
            Role.registerRole(me.chasertw123.villagedefense.game.role.Tank.class, EntityType.IRON_GOLEM);

        this.getCommand("VillageDefense").setExecutor(new VillageDefenseCmd(this));

        new File(getDataFolder().getAbsolutePath() + File.separator + "schematics" + File.separator).mkdirs();

        try {
            arenaFile.createNewFile();
        } catch (IOException e) {
            sendConsoleSevere("Failed to generate arena.yml!");
        }

        arenayml = YamlConfiguration.loadConfiguration(arenaFile);

        try {

            // Regular Monsters
            new Minion();
            new Tank();

            // Boss Monsters
            new BabyTerror();

            Location lobbyLocation = null;

            for (Class<? extends Role> r : Role.roleClasses.keySet())
                if (arenayml.contains("roleselector." + r.getSimpleName()))
                    new RoleSelect(LocationUtils.deserializeLoc(arenayml.getString("roleselector." + r.getSimpleName())), Role.roleClasses.get(r), r).spawnEntity();

            if (arenayml.contains("lobby"))
                lobbyLocation = LocationUtils.deserializeLoc(arenayml.getString("lobby"));

            if (this.getArenaConfig().contains("arena"))
                for (String arenaName : getArenaConfig().getConfigurationSection("arena").getKeys(false)) {

                    String pre = "arena." + arenaName + ".";
                    ArrayList<Building> buildings = new ArrayList<>();

                    for (Class<? extends Building> b : Building.buildingClasses)
                        if (arenayml.contains(pre + "buildings." + b.getSimpleName()))
                            buildings.add(b.getDeclaredConstructor(Location.class).newInstance(LocationUtils.deserializeLoc(arenayml.getString(pre + "buildings." + b.getSimpleName()))));

                    ArrayList<Location> enemySpawnPoints = new ArrayList<>();

                    if (arenayml.contains(pre + "enemyspawns"))
                        for (String s : arenayml.getStringList(pre + "enemyspawns"))
                            enemySpawnPoints.add(LocationUtils.deserializeLoc(s));

                    Location spawnLocation = null;
                    if (arenayml.contains(pre + "spawn"))
                        spawnLocation = LocationUtils.deserializeLoc(arenayml.getString(pre + "spawn"));

                    Arena a = new Arena(arenaName, buildings, enemySpawnPoints, spawnLocation, lobbyLocation, this);

                    arenas.add(a);

                    sendConsoleInfo("Registered Arena: " + a.getName());
                }

            game = new Game(this.getConfig().contains("players.min") ? this.getConfig().getInt("players.min") : 0, this.getConfig().contains("players.max") ? this.getConfig().getInt("players.max") : 0);

        } catch (VillageDefenseException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        voteManager = new VoteManager();

        new Achievements(this);

        for (Player p : Bukkit.getOnlinePlayers()) {

            this.getStatsManager().loadStats(p);

            if (this.getGame() == null)
                continue;

            this.getGame().getPlayers().add(new GamePlayer(null, p));

            if (this.getGame().getPlayers().size() >= this.getGame().getMinPlayers() && this.getGame().getGameState() == GameState.LOBBY)
                this.getGame().startGame(this);

            else if (this.getGame().getGameState() == GameState.DISABLED)
                Bukkit.broadcastMessage(this.getPrefix() + ChatColor.RED + "**Warning the game has not been set up yet** ");

            else if (this.getGame().getPlayers().size() < this.getGame().getMinPlayers()) {
                int amount = this.getGame().getMinPlayers() - this.getGame().getPlayers().size();
                Bukkit.broadcastMessage(this.getPrefix() + "We need " + amount + " more player" + ((amount == 1) ? "" : "s") + " to join!");

                if (this.getGame().getGameState() == GameState.LOBBY)
                    this.getScoreboardManager().giveScoreboard(p, ScoreboardType.VOTING);

                else if (this.getGame().getGameState() == GameState.STARTING)
                    this.getScoreboardManager().giveScoreboard(p, ScoreboardType.STARTING);
            }
        }
    }

    public void onDisable() {

        for (Player p : Bukkit.getOnlinePlayers())
            this.getStatsManager().saveStats(p);

        if (this.getGame() != null)
            if (game.getArena() != null)
                for (Building b : game.getArena().getBuildings()) {
                    b.getVillager().getLoc().getChunk().load();
                    if (b.getVillager().getVil() != null) {
                        for (Entity e : b.getVillager().getVil().getNearbyEntities(1, 1, 1))
                            if (e.getPassenger() == b.getVillager().getVil())
                                e.remove();

                        b.getVillager().getVil().remove();
                    }
                }

        for (RoleSelect rs : RoleSelect.roleSelectObjects) {
            rs.getSpawnLocation().getChunk().load();
            if (rs.getEntity() != null) {
                for (Entity e : rs.getEntity().getNearbyEntities(1, 1, 1))
                    if (e.getPassenger() == rs.getEntity())
                        e.remove();

                rs.getEntity().remove();
            }
        }
    }

    /** Console Messages **/

    public void sendConsoleInfo(String message) {
        this.getLogger().info(message);
    }

    public void sendConsoleWarning(String message) {
        this.getLogger().warning(message);
    }

    public void sendConsoleSevere(String message) {
        this.getLogger().severe(message);
    }

    /** Getters and Setters **/

    public String getPrefix() {
        return prefix;
    }

    public Game getGame() {
        return game;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public VoteManager getVoteManager() {
        return voteManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ArrayList<Arena> getArenas() {
        return arenas;
    }

    public boolean usesSQL() {
        return usesSQL;
    }

    /** YAML Manager **/

    public FileConfiguration getArenaConfig() {
        return arenayml;
    }

    public void saveArenaConfig() {
        try {
            arenayml.save(arenaFile);
        } catch (IOException e) {
            sendConsoleSevere("Failed to save arena.yml!");
        }
    }
}
