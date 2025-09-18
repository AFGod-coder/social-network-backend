package com.example.socialdata.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    @Schema(description = "Nombre del usuario", example = "María")
    private String firstName;

    @Schema(description = "Apellido del usuario", example = "Lopez")
    private String lastName;

    @Schema(description = "Alias público", example = "maria123")
    private String alias;

    @Schema(description = "Correo electrónico", example = "maria@example.com")
    private String email;

    @Schema(description = "Fecha de nacimiento", example = "1998-07-21")
    private LocalDate dateOfBirth;

}
