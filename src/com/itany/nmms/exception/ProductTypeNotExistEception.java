package com.itany.nmms.exception;

public class ProductTypeNotExistEception extends Exception {
    public ProductTypeNotExistEception() {
    }

    public ProductTypeNotExistEception(String message) {
        super(message);
    }

    public ProductTypeNotExistEception(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductTypeNotExistEception(Throwable cause) {
        super(cause);
    }

    public ProductTypeNotExistEception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
