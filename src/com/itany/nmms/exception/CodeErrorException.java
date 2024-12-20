package com.itany.nmms.exception;

public class CodeErrorException extends Exception{
    public CodeErrorException() {
    }

    public CodeErrorException(String message) {
        super(message);
    }

    public CodeErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeErrorException(Throwable cause) {
        super(cause);
    }

    public CodeErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
