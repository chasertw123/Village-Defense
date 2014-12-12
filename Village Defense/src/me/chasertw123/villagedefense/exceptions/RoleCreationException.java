package me.chasertw123.villagedefense.exceptions;

public class RoleCreationException extends VillageDefenseException {

    private static final long serialVersionUID = 3406274503118084314L;

    public RoleCreationException(String message) {
        super(message);
    }

    public RoleCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleCreationException(Throwable cause) {
        super(cause);
    }
}
