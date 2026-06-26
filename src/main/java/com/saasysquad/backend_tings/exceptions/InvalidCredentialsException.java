package com.saasysquad.backend_tings.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String error) {
        super(error);
    }

    public InvalidCredentialsException(String error, Throwable e) {
        super(error, e);
    }
}
