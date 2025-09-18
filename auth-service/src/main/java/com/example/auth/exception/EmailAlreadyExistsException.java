package com.example.auth.exception;
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Correo electrónico ya registrado: " + email);
    }
}
