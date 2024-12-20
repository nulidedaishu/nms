package com.itany.nmms.exception;

public class FileUploadErrorException extends Exception{
    public FileUploadErrorException() {
    }

    public FileUploadErrorException(String message) {
        super(message);
    }

    public FileUploadErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadErrorException(Throwable cause) {
        super(cause);
    }

    public FileUploadErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
