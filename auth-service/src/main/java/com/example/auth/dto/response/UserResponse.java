package com.example.auth.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String firstName,
        String lastName,
        String alias,
        String role,
        LocalDateTime createdAt
) {}
