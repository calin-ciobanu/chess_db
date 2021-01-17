package com.calinux.chessdb.api.v1.exception;

public class GameImportException extends RuntimeException {
    public GameImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
