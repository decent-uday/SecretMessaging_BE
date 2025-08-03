package com.secret.io.advice;

public class AuthNotFoundException extends RuntimeException {

    public AuthNotFoundException(String message) {
        super(message);
    }
}
