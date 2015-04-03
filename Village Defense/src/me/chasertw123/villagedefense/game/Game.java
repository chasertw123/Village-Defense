package me.chasertw123.villagedefense.game;

import java.util.ArrayList;

import me.chasertw123.villagedefense.Main;
import me.chasertw123.villagedefense.exceptions.GameCreationException;
import me.chasertw123.villagedefense.game.arena.Arena;
import me.chasertw123.villagedefense.game.wave.Wave;
import me.chasertw123.villagedefense.timers.LobbyTimer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Game {

    private Arena arena;
    private int minPlayers, maxPlayers;

    private GameState gameState = GameState.DISABLED;
    private Wave wave;

    private ArrayList<GamePlayer> players = new ArrayList<GamePlayer>();

    /**
     * Create a new {@link Game} instance
     * 
     * @param arena the {@link Arena} the game will be in
     * @param minPlayers the minimum players to start a {@link Game}
     * @param maxPlayers the maximum players per a {@link Game}
     * @throws GameCreationException when minimum and maximum players are set
     * incorrectly
     */
    public Game(int minPlayers, int maxPlayers) throws GameCreationException {

        if (minPlayers == 0 || maxPlayers == 0)
            throw new GameCreationException("Either minimum players or maximum players was set to 0!");

        if (minPlayers > maxPlayers)
            throw new GameCreationException("The minimum amount of players is larger than the maximum." + " (MinPlayers: " + minPlayers + " > MaxPlayers: " + maxPlayers);

        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.gameState = GameState.LOBBY;
    }

    /**
     * 
     * @return the {@link Arena} the game is taking place in
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * 
     * @param arena {@link Arena} this {@link Game} instance uses
     */
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    /**
     * 
     * @return boolean of if the {@link Game} is full
     */
    public boolean isFull() {
        if (maxPlayers <= players.size())
            return true;

        return false;
    }

    /**
     * 
     * @return the minimum of players in this instance of {@link Game}
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * 
     * @return the maximum of players in this instance of {@link Game}
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * 
     * @return a {@link GamePlayer} array list for the {@link Game} instance
     */
    public ArrayList<GamePlayer> getPlayers() {
        return players;
    }

    /**
     * 
     * @param player The {@link Player} you wish to get from {@link GamePlayer}
     * @return {@link GamePlayer} from specified {@link Player}
     */
    public GamePlayer getGamePlayer(Player player) {
        for (GamePlayer gp : this.getPlayers())
            if (gp.isEqualToPlayer(player))
                return gp;

        return null;
    }

    /**
     * 
     * @return the {@link GameState} that the {@link Game} instance has set
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Update the {@link GameState}
     * 
     * @param gameState new {@link GameState} for the {@link Game} instance
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * 
     * @return the current {@link Wave} instance
     */
    public Wave getWave() {
        return wave;
    }

    /**
     * Update the current {@link Wave}
     * 
     * @param wave a new instance of {@link Wave}
     */
    public void setWave(Wave wave) {
        this.wave = wave;
    }

    /**
     * 
     * @param plugin {@link JavaPlugin} of program
     */
    public void startGame(Main plugin) {

        setGameState(GameState.STARTING);
        new LobbyTimer(plugin);

    }

}
