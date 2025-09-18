package com.example.socialdata.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLikeRequest {
    @NotNull
    @Schema(description = "ID del usuario que da el like", example = "1")
    private Long userId;
}
