package com.example.bff.exception;

public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(String serviceName, String message) {
        super("Error en el servicio externo " + serviceName + ": " + message);
    }
    
    public ExternalServiceException(String message) {
        super(message);
    }
}