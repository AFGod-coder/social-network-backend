package com.example.auth.service;

import com.example.auth.entity.User;

public interface JwtService {
    String generateToken(User user);
    boolean validateToken(String token);
    String extractUsername(String token);
}
