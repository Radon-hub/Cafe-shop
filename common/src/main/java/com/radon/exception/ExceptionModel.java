package com.radon.exception;

public class ExceptionModel extends RuntimeException {

    private final String message;

    public ExceptionModel(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
