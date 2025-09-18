package com.example.business.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información básica de un usuario")
public class UserDto {

    @Schema(description = "ID del usuario", example = "1")
    private Long id;

    @Schema(description = "Correo electrónico del usuario", example = "andres@gmail.com")
    private String email;

    @Schema(description = "Nombre del usuario", example = "Andres")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Gonzalez")
    private String lastName;

    @Schema(description = "Alias del usuario", example = "andre")
    private String alias;

    @Schema(description = "Rol del usuario", example = "USER")
    private String role;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @Schema(description = "Fecha de creación del usuario")
    private LocalDateTime createdAt;
}
