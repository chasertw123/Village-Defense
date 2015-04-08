package me.chasertw123.villagedefense.exceptions;

public class BuildingSpawnException extends Exception {

    private static final long serialVersionUID = 3804587523814328002L;

    public BuildingSpawnException(String message) {
        super(message);
    }

    public BuildingSpawnException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildingSpawnException(Throwable cause) {
        super(cause);
    }
}
