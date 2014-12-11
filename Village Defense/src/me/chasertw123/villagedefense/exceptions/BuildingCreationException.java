package me.chasertw123.villagedefense.exceptions;

public class BuildingCreationException extends Exception {

	private static final long serialVersionUID = -832160838356818672L;

	public BuildingCreationException(String message) {
		super(message);
	}
	
	public BuildingCreationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BuildingCreationException(Throwable cause) {
		super(cause);
	}
}
