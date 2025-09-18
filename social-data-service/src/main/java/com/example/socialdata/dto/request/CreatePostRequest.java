package com.example.socialdata.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePostRequest {
    @NotBlank
    @Schema(description = "Mensaje del post", example = "Hola, este es mi primer post!")
    private String message;

    @NotNull
    @Schema(description = "ID del autor del post", example = "1")
    private Long authorId;
}
