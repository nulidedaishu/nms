package com.itany.nmms.exception;

public class DeptStatusException extends Exception{
    public DeptStatusException() {
    }

    public DeptStatusException(String message) {
        super(message);
    }

    public DeptStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeptStatusException(Throwable cause) {
        super(cause);
    }

    public DeptStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
