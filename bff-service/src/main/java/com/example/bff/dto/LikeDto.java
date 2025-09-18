package com.example.bff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información de un like")
public class LikeDto {

    @Schema(description = "ID del like", example = "1")
    private Long id;

    @Schema(description = "ID del usuario que dio el like", example = "1")
    private Long userId;

    @Schema(description = "ID del post al que se le dio like", example = "1")
    private Long postId;

    @Schema(description = "Fecha de creación del like")
    private LocalDateTime createdAt;
}