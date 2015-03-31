package me.chasertw123.villagedefense.events;

import me.chasertw123.villagedefense.stats.achievements.Achievement;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AchievementUnlockEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private Player player;

    private Achievement achievement;
    private boolean cancelled;

    public AchievementUnlockEvent(Player player, Achievement achievement) {
        this.player = player;
        this.achievement = achievement;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return the achievement
     */
    public Achievement getAchievement() {
        return achievement;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
