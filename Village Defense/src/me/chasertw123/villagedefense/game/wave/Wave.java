package me.chasertw123.villagedefense.game.wave;

import org.bukkit.Bukkit;

public class Wave {

    private int wave, difficulty;
    private boolean bossWave;

    /** Create a new instance of {@link Wave}
     * 
     * @param wave
     *            the wave number
     * @param difficulty
     *            the level of difficulty
     * @param bossWave
     *            boolean of if it ia a boss wave */
    public Wave(int wave, int difficulty, boolean bossWave) {
        this.wave = wave;
        this.difficulty = difficulty;
        this.bossWave = bossWave;

        Bukkit.getServer().getPluginManager().callEvent(new WaveCreateEvent(this));
    }

    /** @return the current wave number of this {@link Wave} instance */
    public int getWaveNumber() {
        return wave;
    }

    /** @return the difficulty of this {@link Wave} instance */
    public int getDifficulty() {
        return difficulty;
    }

    /** Update difficulty
     * 
     * @param difficulty
     *            the new difficulty of this {@link Wave} */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /** @return boolean of boss wave for this {@link Wave} */
    public boolean isBossWave() {
        return bossWave;
    }

    /** Update boss wave
     * 
     * @param bossWave
     *            the new boolean of if this is a boss wave */
    public void setBossWave(boolean bossWave) {
        this.bossWave = bossWave;
    }
}
