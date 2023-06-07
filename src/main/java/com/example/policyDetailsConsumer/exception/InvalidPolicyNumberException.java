package com.example.policyDetailsConsumer.exception;

public class InvalidPolicyNumberException extends RuntimeException {
    public InvalidPolicyNumberException(String message) {
        super(message);
    }
}
