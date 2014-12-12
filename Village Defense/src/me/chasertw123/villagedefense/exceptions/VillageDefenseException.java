package me.chasertw123.villagedefense.exceptions;

public class VillageDefenseException extends Exception {

    private static final long serialVersionUID = 7075710927689637527L;

    public VillageDefenseException(String message) {
        super(message);
    }

    public VillageDefenseException(String message, Throwable cause) {
        super(message, cause);
    }

    public VillageDefenseException(Throwable cause) {
        super(cause);
    }
}
