package com.example.moneyAllocation.exception;

public class HealthDieException extends RuntimeException {
    public HealthDieException(String message) {
        super(message);
    }

    public HealthDieException(String message, Throwable cause) {
        super(message, cause);
    }
}
