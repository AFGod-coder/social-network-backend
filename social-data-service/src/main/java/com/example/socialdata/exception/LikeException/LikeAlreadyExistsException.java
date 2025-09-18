package com.example.socialdata.exception.LikeException;

public class LikeAlreadyExistsException extends RuntimeException {
    public LikeAlreadyExistsException(Long userId, Long postId) {
        super("El usuario " + userId + " ya dio 'like' a la publicación " + postId);
    }
}
