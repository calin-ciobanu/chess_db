package com.calinux.chessdb.api.v1.exception;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(Long id, Throwable cause) {
        super(String.format("Could not find job with id %d", id), cause);
    }
}
