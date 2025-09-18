package com.example.socialdata.exception.PostException;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Post con id '" + id + "' no encontrado.");
    }
}
