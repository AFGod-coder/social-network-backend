package com.example.auth.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        Long userId
) {}
