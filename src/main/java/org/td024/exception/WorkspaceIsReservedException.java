package org.td024.exception;

public class WorkspaceIsReservedException extends RuntimeException {
    public WorkspaceIsReservedException(String message) {
        super(message);
    }
}
