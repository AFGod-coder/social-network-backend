package com.example.bff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request para crear un like")
public class CreateLikeRequest {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    @Schema(description = "ID del usuario que da el like", example = "1", required = true)
    private Long userId;
}