package me.chasertw123.villagedefense.events;

import java.util.ArrayList;

import me.chasertw123.villagedefense.game.Game;
import me.chasertw123.villagedefense.game.GamePlayer;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Game game;

    public GameStartEvent(Game game) {
        this.game = game;
    }

    /**
     * 
     * @return {@link Integer} amount of minimum amount of players
     */
    public int getMinPlayers() {
        return game.getMinPlayers();
    }

    /**
     * 
     * @return {@link Integer} amount of maximum amount of players
     */
    public int getMaxPlayers() {
        return game.getMinPlayers();
    }

    /**
     * 
     * @return a {@link GamePlayer} {@link ArrayList} containing all the players
     * in the {@link Game}
     */
    public ArrayList<GamePlayer> getPlayers() {
        return game.getPlayers();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
