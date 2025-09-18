package com.example.bff.controller;

import com.example.bff.dto.*;
import com.example.bff.service.BffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bff")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "BFF - Business Logic", description = "Endpoints del BFF para lógica de negocio")
@SecurityRequirement(name = "Bearer Authentication")
public class BffController {

    private final BffService service;

    @Operation(summary = "Obtener usuario completo", description = "Obtiene la información completa de un usuario combinando datos de autenticación y sociales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable @NotNull Long id) {
        log.info("Solicitando usuario completo con ID: {}", id);
        UserDto user = service.getUser(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Obtener feed de usuario", description = "Obtiene el feed de publicaciones de otros usuarios para un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feed obtenido exitosamente"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getFeed(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @RequestParam @NotNull Long userId) {
        log.info("Solicitando feed para usuario: {}", userId);
        List<PostDto> feed = service.getFeed(userId);
        return ResponseEntity.ok(feed);
    }

    @Operation(summary = "Crear nueva publicación", description = "Crea una nueva publicación en la red social")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publicación creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(
            @Parameter(description = "Datos de la nueva publicación", required = true)
            @Valid @RequestBody CreatePostRequest request) {
        log.info("Creando nueva publicación para usuario: {}", request.getAuthorId());
        PostDto post = service.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @Operation(summary = "Agregar like a publicación", description = "Agrega un like a una publicación específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Like agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<LikeDto> addLike(
            @Parameter(description = "ID de la publicación", example = "1", required = true)
            @PathVariable @NotNull Long postId,
            @Parameter(description = "Datos del like", required = true)
            @Valid @RequestBody CreateLikeRequest request) {
        log.info("Agregando like al post: {} por usuario: {}", postId, request.getUserId());
        LikeDto like = service.addLike(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(like);
    }

    @Operation(summary = "Contar likes de publicación", description = "Obtiene el número total de likes de una publicación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autorizado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @GetMapping("/posts/{postId}/likes/count")
    public ResponseEntity<Integer> countLikes(
            @Parameter(description = "ID de la publicación", example = "1", required = true)
            @PathVariable @NotNull Long postId) {
        log.info("Contando likes del post: {}", postId);
        Integer count = service.countLikes(postId);
        return ResponseEntity.ok(count);
    }
}
