package com.calinux.chessdb.api.v1.exception;

public class ChessUserNotFoundException extends RuntimeException {
    public ChessUserNotFoundException(Long id) {
        super(String.format("User with id %d was not found", id));
    }
}
