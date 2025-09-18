package com.example.auth.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SocialUserResponse(
        Long id,
        String firstName,
        String lastName,
        String alias,
        String email,
        LocalDate dateOfBirth,
        LocalDateTime createdAt
) {}
