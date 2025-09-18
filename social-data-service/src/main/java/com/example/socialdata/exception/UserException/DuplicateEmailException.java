package com.example.socialdata.exception.UserException;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("El email '" + email + "' ya está registrado");
    }
}