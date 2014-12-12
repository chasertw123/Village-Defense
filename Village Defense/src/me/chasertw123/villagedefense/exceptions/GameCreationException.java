package me.chasertw123.villagedefense.exceptions;

public class GameCreationException extends Exception {

    private static final long serialVersionUID = 3406274503118084314L;

    public GameCreationException(String message) {
        super(message);
    }

    public GameCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameCreationException(Throwable cause) {
        super(cause);
    }
}
