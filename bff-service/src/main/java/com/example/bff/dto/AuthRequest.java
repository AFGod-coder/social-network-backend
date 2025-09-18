package com.example.bff.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record AuthRequest(
        @NotBlank String email,
        @NotBlank String password
) implements Serializable {}