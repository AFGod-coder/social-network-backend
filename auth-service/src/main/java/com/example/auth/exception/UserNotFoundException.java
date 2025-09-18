package com.example.auth.exception;
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Usuario no encontrado con ID " + id);
    }
}
