package me.chasertw123.villagedefense.game;

import java.util.ArrayList;

import me.chasertw123.villagedefense.exceptions.GameCreationException;
import me.chasertw123.villagedefense.game.arena.Arena;

public class Game {

	private Arena arena;
	private int minPlayers, maxPlayers;
	private GameState gameState = GameState.DISABLED;
	private ArrayList<GamePlayer> players = new ArrayList<GamePlayer>();
	
	public Game(Arena arena, int minPlayers, int maxPlayers) throws GameCreationException {
		this.arena = arena;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
		
		if (minPlayers > maxPlayers)
			throw new GameCreationException("The minimum amount of players is larger than the maximum."
					+ " (MinPlayers: " + minPlayers + " > MaxPlayers: " + maxPlayers);
	}
	
	public Arena getArena() {
		return arena;
	}
	
	public boolean isFull() {
		if (maxPlayers <= players.size())
			return true;
		
		return false;
	}
	
	public int getMinPlayers() {
		return minPlayers;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public ArrayList<GamePlayer> getPlayers() {
		return players;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
}
