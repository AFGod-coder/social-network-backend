package com.example.socialdata.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserRequest {
    @NotBlank
    @Schema(description = "Nombre del usuario", example = "Andrés")
    private String firstName;

    @NotBlank
    @Schema(description = "Apellido del usuario", example = "González")
    private String lastName;

    @NotBlank
    @Schema(description = "Alias público", example = "andresg")
    private String alias;

    @Email @NotBlank
    @Schema(description = "Email único", example = "andres@example.com")
    private String email;

    @Schema(description = "Fecha de nacimiento", example = "2000-05-10")
    private LocalDate dateOfBirth;
}
