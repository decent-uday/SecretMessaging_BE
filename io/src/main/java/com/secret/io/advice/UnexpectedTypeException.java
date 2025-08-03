package com.secret.io.advice;

public class UnexpectedTypeException extends RuntimeException {

    UnexpectedTypeException(String message) {
        super(message);
    }
}
