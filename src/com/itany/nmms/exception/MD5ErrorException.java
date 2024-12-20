package com.itany.nmms.exception;

public class MD5ErrorException extends RuntimeException {
    public MD5ErrorException() {
    }

    public MD5ErrorException(String message) {
        super(message);
    }

    public MD5ErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public MD5ErrorException(Throwable cause) {
        super(cause);
    }

    public MD5ErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
