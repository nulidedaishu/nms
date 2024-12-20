package com.itany.nmms.exception;

public class ObjectFactoryErrorException extends RuntimeException{
    public ObjectFactoryErrorException() {
    }

    public ObjectFactoryErrorException(String message) {
        super(message);
    }

    public ObjectFactoryErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectFactoryErrorException(Throwable cause) {
        super(cause);
    }

    public ObjectFactoryErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
