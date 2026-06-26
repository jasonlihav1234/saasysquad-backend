package com.saasysquad.backend_tings.exceptions;


public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String error) {
        super(error);
    }

    public UserAlreadyExistsException(String error, Throwable cause) {
        super(error, cause);
    }
}
