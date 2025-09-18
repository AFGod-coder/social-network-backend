
package com.example.bff.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Respuesta de autenticación")
public record AuthResponse(
        @Schema(description = "Token de acceso JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,
        
        @Schema(description = "Token de actualización", example = "refresh_token_123")
        String refreshToken,
        
        @Schema(description = "ID del usuario autenticado", example = "1")
        Long userId
) implements Serializable {}