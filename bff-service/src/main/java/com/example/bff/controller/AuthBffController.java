package com.example.bff.controller;

import com.example.bff.dto.*;
import com.example.bff.service.BffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bff/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "BFF - Authentication", description = "Endpoints del BFF para autenticación")
public class AuthBffController {

    private final BffService service;

    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "502", description = "Error en servicio de autenticación")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "Credenciales de login", required = true)
            @Valid @RequestBody AuthRequest request) {
        AuthResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o usuario ya existe"),
            @ApiResponse(responseCode = "502", description = "Error en servicio de autenticación")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Parameter(description = "Datos del nuevo usuario", required = true)
            @Valid @RequestBody RegisterRequest request) {
        AuthResponse response = service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener usuario de autenticación", description = "Obtiene información básica de un usuario desde el servicio de autenticación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio de autenticación")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable @NotNull Long id) {
        UserDto user = service.getAuthUser(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Renovar token de acceso", description = "Renueva el token de acceso usando el refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token renovado exitosamente"),
            @ApiResponse(responseCode = "401", description = "Refresh token inválido o expirado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio de autenticación")
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @Parameter(description = "Refresh token", required = true)
            @Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = service.refreshToken(request);
        return ResponseEntity.ok(response);
    }
}
