package me.chasertw123.villagedefense.events;

import me.chasertw123.villagedefense.game.wave.Wave;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WaveCreateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Wave wave;

    public WaveCreateEvent(Wave wave) {
        this.wave = wave;
    }

    /**
     * @return the current wave number of this {@link Wave} instance
     */
    public int getWaveNumber() {
        return wave.getWaveNumber();
    }

    /**
     * @return the difficulty of this {@link Wave} instance
     */
    public int getDifficulty() {
        return wave.getDifficulty();
    }

    /**
     * Update difficulty
     * 
     * @param difficulty the new difficulty of this {@link Wave}
     */
    public void setDifficulty(int difficulty) {
        wave.setDifficulty(difficulty);
    }

    /**
     * @return boolean of boss wave for this {@link Wvae}
     */
    public boolean isBossWave() {
        return wave.isBossWave();
    }

    /**
     * Update boss wave
     * 
     * @param bossWave the new boolean of if this is a boss wave
     */
    public void setBossWave(boolean bossWave) {
        wave.setBossWave(bossWave);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
