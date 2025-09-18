package com.example.auth.dto.request;

import java.time.LocalDate;

public record UserRequest(
        String firstName,
        String lastName,
        String alias,
        String email,
        LocalDate dateOfBirth
) {}
