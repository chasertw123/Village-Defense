package me.chasertw123.villagedefense.game.wave;

import java.util.Random;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.events.WaveCreateEvent;
import me.chasertw123.villagedefense.exceptions.InvalidEnemySpawnExcpetion;
import me.chasertw123.villagedefense.game.enemy.Enemy;

import org.bukkit.Bukkit;

public class Wave {

    private int wave, difficulty;
    private boolean bossWave;
    private Main plugin;

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

    public void startWave() throws InvalidEnemySpawnExcpetion {

        Random r = new Random();
        int currentDifficulty = 0;

        while (currentDifficulty < difficulty) {

            Enemy e = Enemy.enemyObjects.get(r.nextInt(Enemy.enemyObjects.size()));

            currentDifficulty += e.getDifficulty();
            e.spawnEntity(plugin.getGame().getArena().getEnemySpawnPoints().get(r.nextInt(plugin.getGame().getArena().getEnemySpawnPoints().size())), plugin);
        }

        currentDifficulty = 0;

        if (isBossWave())
            while (currentDifficulty < difficulty) {

                Enemy e = Enemy.enemyObjects.get(r.nextInt(Enemy.bossEnemyObjects.size()));

                currentDifficulty += e.getDifficulty();
                e.spawnEntity(plugin.getGame().getArena().getEnemySpawnPoints().get(r.nextInt(plugin.getGame().getArena().getEnemySpawnPoints().size())), plugin);
            }
    }
}
