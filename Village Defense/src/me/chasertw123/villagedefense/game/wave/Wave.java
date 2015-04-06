package me.chasertw123.villagedefense.game.wave;

import java.util.ArrayList;
import java.util.Random;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.events.WaveCreateEvent;
import me.chasertw123.villagedefense.exceptions.InvalidEnemySpawnExcpetion;
import me.chasertw123.villagedefense.game.GamePlayer;
import me.chasertw123.villagedefense.game.enemy.Enemy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;

public class Wave {

    private int wave, difficulty;
    private boolean bossWave;
    private Main plugin;
    private ArrayList<LivingEntity> enemies = new ArrayList<>();

    /**
     * Create a new instance of {@link Wave}
     * 
     * @param wave the wave number
     * @param difficulty the level of difficulty
     * @param bossWave boolean of if it ia a boss wave
     */
    public Wave(int wave, int difficulty, boolean bossWave, Main plugin) {
        this.wave = wave;
        this.difficulty = difficulty;
        this.bossWave = bossWave;
        this.plugin = plugin;

        Bukkit.getServer().getPluginManager().callEvent(new WaveCreateEvent(this));
    }

    /**
     * @return the current wave number of this {@link Wave} instance
     */
    public int getWaveNumber() {
        return wave;
    }

    /**
     * @return the difficulty of this {@link Wave} instance
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Update difficulty
     * 
     * @param difficulty the new difficulty of this {@link Wave}
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return boolean of boss wave for this {@link Wave}
     */
    public boolean isBossWave() {
        return bossWave;
    }

    /**
     * Update boss wave
     * 
     * @param bossWave the new boolean of if this is a boss wave
     */
    public void setBossWave(boolean bossWave) {
        this.bossWave = bossWave;
    }

    /**
     * Get current progress of wave
     * 
     * @return {@link Float} value of this {@link Wave}
     */
    public float getProgress() {

        int livingCount = 0;

        for (LivingEntity le : enemies)
            if (le != null && !le.isDead())
                livingCount++;

        if (livingCount == 0)
            return 0f;

        return enemies.size() / livingCount;
    }

    /**
     * Get enemies left
     * 
     * @return the number of enemies left in the {@link Wave}
     */
    public int getEnemiesLeft() {

        int livingCount = 0;

        for (LivingEntity le : enemies)
            if (le != null && !le.isDead())
                livingCount++;

        return livingCount;
    }

    /**
     * @return the enemies a {@link ArrayList} of {@link LivingEntity} that are
     * spawned from {@link Enemy}
     */
    public ArrayList<LivingEntity> getEnemies() {
        return enemies;
    }

    /**
     * @param enemies the enemies to set new {@link ArrayList} of
     * {@link LivingEntity}
     */
    public void setEnemies(ArrayList<LivingEntity> enemies) {
        this.enemies = enemies;
    }

    public void startWave() throws InvalidEnemySpawnExcpetion {

        Random r = new Random();
        int currentDifficulty = 0;

        while (currentDifficulty < difficulty) {

            Enemy e = Enemy.enemyObjects.get(r.nextInt(Enemy.enemyObjects.size()));

            if (e.getDifficulty() + currentDifficulty <= difficulty) {
                currentDifficulty += e.getDifficulty();
                enemies.add(e.spawnEntity(plugin.getGame().getArena().getEnemySpawnPoints().get(r.nextInt(plugin.getGame().getArena().getEnemySpawnPoints().size())), plugin));
            }
        }

        currentDifficulty = 0;

        if (Enemy.bossEnemyObjects.size() > 0 && isBossWave()) {

            Enemy e = Enemy.bossEnemyObjects.get(r.nextInt(Enemy.bossEnemyObjects.size()));

            enemies.add(e.spawnEntity(plugin.getGame().getArena().getEnemySpawnPoints().get(r.nextInt(plugin.getGame().getArena().getEnemySpawnPoints().size())), plugin));

            for (GamePlayer gp : plugin.getGame().getPlayers())
                gp.sendMessage(plugin.getPrefix() + ChatColor.YELLOW + "A " + ChatColor.BLUE + ChatColor.stripColor(e.getCustomName()) + ChatColor.YELLOW + " has spawned!");
        }
    }
}
