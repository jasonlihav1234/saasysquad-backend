package com.saasysquad.backend_tings.exceptions;

public class ResetPasswordTokenInvalidException extends RuntimeException {
    public ResetPasswordTokenInvalidException(String message) {
        super(message);
    }
}
