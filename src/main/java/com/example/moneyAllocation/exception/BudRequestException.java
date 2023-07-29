package com.example.moneyAllocation.exception;

public class BudRequestException extends RuntimeException {
    public BudRequestException(String message) {
        super(message);
    }

    public BudRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
