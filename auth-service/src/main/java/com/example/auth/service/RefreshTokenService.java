package com.example.auth.service;

import com.example.auth.dto.response.AuthResponse;
import com.example.auth.entity.RefreshToken;
import com.example.auth.entity.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    AuthResponse refreshAccessToken(String refreshToken);
}
