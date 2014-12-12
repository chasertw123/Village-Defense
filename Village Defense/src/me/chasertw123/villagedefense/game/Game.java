package me.chasertw123.villagedefense.game;

import java.util.ArrayList;

import me.chasertw123.villagedefense.exceptions.GameCreationException;
import me.chasertw123.villagedefense.game.arena.Arena;

public class Game {

    private Arena arena;
    private int minPlayers, maxPlayers;
    private GameState gameState = GameState.DISABLED;
    private ArrayList<GamePlayer> players = new ArrayList<GamePlayer>();

    /** Create a new {@link Game} instance
     * 
     * @param arena
     *            the {@link Arena} the game will be in
     * @param minPlayers
     *            the minimum players to start a {@link Game}
     * @param maxPlayers
     *            the maximum players per a {@link Game}
     * @throws GameCreationException
     *             when minimum and maximum players are set incorrectly */
    public Game(Arena arena, int minPlayers, int maxPlayers) throws GameCreationException {
        this.arena = arena;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;

        if (minPlayers > maxPlayers)
            throw new GameCreationException("The minimum amount of players is larger than the maximum." + " (MinPlayers: " + minPlayers + " > MaxPlayers: " + maxPlayers);
    }

    /** @return the {@link Arena} the game is taking place in */
    public Arena getArena() {
        return arena;
    }

    /** @return boolean of if the {@link Game} is full */
    public boolean isFull() {
        if (maxPlayers <= players.size())
            return true;

        return false;
    }

    /** @return the minimum of players in this instance of {@link Game} */
    public int getMinPlayers() {
        return minPlayers;
    }

    /** @return the maximum of players in this instance of {@link Game} */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /** @return a {@link GamePlayer} array list for the {@link Game} instance */
    public ArrayList<GamePlayer> getPlayers() {
        return players;
    }

    /** @return the {@link GameState} that the {@link Game} instance has set */
    public GameState getGameState() {
        return gameState;
    }

    /** Update the {@link GameState}
     * 
     * @param gameState
     *            new {@link GameState} for the {@link Game} instance */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
