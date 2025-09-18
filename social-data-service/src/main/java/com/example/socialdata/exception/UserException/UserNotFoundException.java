package com.example.socialdata.exception.UserException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Usuario con id '" + id + "' no encontrado.");
    }
}
