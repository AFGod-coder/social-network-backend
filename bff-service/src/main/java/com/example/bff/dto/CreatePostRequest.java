package com.example.bff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request para crear un post")
public class CreatePostRequest {

    @NotNull(message = "El ID del autor es obligatorio")
    @Schema(description = "ID del autor del post", example = "1", required = true)
    private Long authorId;

    @NotBlank(message = "El contenido del post no puede estar vacío")
    @Schema(description = "Contenido del post", example = "Aprendiendo microservicios con Spring Boot", required = true)
    private String message;
}