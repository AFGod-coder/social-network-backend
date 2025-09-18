package com.example.business.exception;

public class BusinessClientException extends RuntimeException {
    public BusinessClientException(String message) {
        super(message);
    }
}