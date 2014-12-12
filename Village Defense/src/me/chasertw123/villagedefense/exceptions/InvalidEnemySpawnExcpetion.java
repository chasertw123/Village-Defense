package me.chasertw123.villagedefense.exceptions;

public class InvalidEnemySpawnExcpetion extends VillageDefenseException {

    private static final long serialVersionUID = 3742481536927600335L;

    public InvalidEnemySpawnExcpetion(String message) {
        super(message);
    }

    public InvalidEnemySpawnExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEnemySpawnExcpetion(Throwable cause) {
        super(cause);
    }

}
