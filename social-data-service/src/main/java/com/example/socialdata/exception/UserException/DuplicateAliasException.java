package com.example.socialdata.exception.UserException;

public class DuplicateAliasException extends RuntimeException {
    public DuplicateAliasException(String alias) {
        super("El alias '" + alias + "' ya está en uso");
    }
}