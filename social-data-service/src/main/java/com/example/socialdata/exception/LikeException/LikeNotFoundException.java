package com.example.socialdata.exception.LikeException;

public class LikeNotFoundException extends RuntimeException {
    public LikeNotFoundException(Long id) {
        super("No se encontró 'like' con ID " + id);
    }
}
