package me.chasertw123.villagedefense.exceptions;

public class DuplicateAchievementIDException extends VillageDefenseException {

    private static final long serialVersionUID = 5384804020723133413L;

    public DuplicateAchievementIDException(String message) {
        super(message);
    }

    public DuplicateAchievementIDException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAchievementIDException(Throwable cause) {
        super(cause);
    }

}
