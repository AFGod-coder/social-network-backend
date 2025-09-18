package com.example.bff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un post")
public class PostDto {

    @Schema(description = "ID del post", example = "1")
    private Long id;

    @Schema(description = "ID del autor del post", example = "1")
    private Long authorId;

    @Schema(description = "Contenido del post", example = "Hoy aprendí sobre microservicios")
    private String message;

    @Schema(description = "Alias del autor del post", example = "andre")
    private String authorAlias;

    @Schema(description = "Fecha de creación del post")
    private LocalDateTime createdAt;
}