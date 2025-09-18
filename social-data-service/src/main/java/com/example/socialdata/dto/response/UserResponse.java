package com.example.socialdata.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String alias,
        String email,
        LocalDate dateOfBirth,
        LocalDateTime createdAt
) {}
