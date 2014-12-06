package me.chasertw123.villagedefense.game.arena;

public class ArenaCreationException extends Exception {

	private static final long serialVersionUID = 5384804020723133413L;
	
	public ArenaCreationException(String message) {
		super(message);
	}
	
	public ArenaCreationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ArenaCreationException(Throwable cause) {
		super(cause);
	}

}
