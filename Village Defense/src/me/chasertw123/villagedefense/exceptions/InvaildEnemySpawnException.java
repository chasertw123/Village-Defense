package me.chasertw123.villagedefense.exceptions;

public class InvaildEnemySpawnException extends Exception {

	private static final long serialVersionUID = 3742481536927600335L;

	public InvaildEnemySpawnException(String message) {
		super(message);
	}
	
	public InvaildEnemySpawnException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvaildEnemySpawnException(Throwable cause) {
		super(cause);
	}
	
}
