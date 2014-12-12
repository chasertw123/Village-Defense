package me.chasertw123.villagedefense.exceptions;

public class AbilityCreationException extends Exception {

    private static final long serialVersionUID = 7075710927689637527L;

    public AbilityCreationException(String message) {
        super(message);
    }

    public AbilityCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbilityCreationException(Throwable cause) {
        super(cause);
    }
}
