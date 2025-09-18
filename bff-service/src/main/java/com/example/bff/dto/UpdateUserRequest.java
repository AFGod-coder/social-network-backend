package com.example.bff.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateUserRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String firstName,
        
        @NotBlank(message = "El apellido es requerido")
        @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
        String lastName,
        
        @NotBlank(message = "El alias es requerido")
        @Size(min = 3, max = 30, message = "El alias debe tener entre 3 y 30 caracteres")
        String alias,
        
        @Email(message = "El formato del email no es válido")
        @NotBlank(message = "El email es requerido")
        String email,
        
        LocalDate dateOfBirth
) {}
