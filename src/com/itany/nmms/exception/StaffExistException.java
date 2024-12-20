package com.itany.nmms.exception;

public class StaffExistException extends Exception {
    public StaffExistException() {
    }

    public StaffExistException(String message) {
        super(message);
    }

    public StaffExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public StaffExistException(Throwable cause) {
        super(cause);
    }

    public StaffExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
