package com.calinux.chessdb.api.v1.exception;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException(Long id) {
        super(String.format("Could not find job with id %d", id));
    }
}
