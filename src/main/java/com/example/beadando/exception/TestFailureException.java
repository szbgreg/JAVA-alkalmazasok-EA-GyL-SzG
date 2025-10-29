package com.example.beadando.exception;

public class TestFailureException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public TestFailureException(String message) {
        super("FAILURE:" + message);
    }

    public TestFailureException(Exception e) {
        super(e);
    }
}