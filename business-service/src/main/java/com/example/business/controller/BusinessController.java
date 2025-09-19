package com.example.business.controller;

import com.example.business.dto.*;
import com.example.business.service.BusinessService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/business")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Business Service", description = "Endpoints del servicio de negocio")
public class BusinessController {

    private final BusinessService service;

    @Operation(summary = "Obtener usuario por ID", description = "Obtiene la información completa de un usuario combinando datos de autenticación y sociales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable @NotNull Long id) {
        log.info("Solicitando usuario con ID: {}", id);
        UserDto user = service.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Obtener feed de usuario", description = "Obtiene el feed de publicaciones de otros usuarios para un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feed obtenido exitosamente"),
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

    @Operation(summary = "Obtener todas las publicaciones", description = "Obtiene todas las publicaciones del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicaciones obtenidas exitosamente"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        log.info("Obteniendo todas las publicaciones");
        List<PostDto> posts = service.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Obtener publicación por ID", description = "Obtiene una publicación específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publicación encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(
            @Parameter(description = "ID de la publicación", example = "1", required = true)
            @PathVariable @NotNull Long id) {
        log.info("Obteniendo publicación con ID: {}", id);
        PostDto post = service.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "Eliminar publicación", description = "Elimina una publicación específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Publicación eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "ID de la publicación", example = "1", required = true)
            @PathVariable @NotNull Long id) {
        log.info("Eliminando publicación con ID: {}", id);
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener likes de publicación", description = "Obtiene todos los likes de una publicación específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Likes obtenidos exitosamente"),
            @ApiResponse(responseCode = "404", description = "Publicación no encontrada"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @GetMapping("/posts/{postId}/likes")
    public ResponseEntity<List<LikeDto>> getLikes(
            @Parameter(description = "ID de la publicación", example = "1", required = true)
            @PathVariable @NotNull Long postId) {
        log.info("Obteniendo likes del post: {}", postId);
        List<LikeDto> likes = service.getLikes(postId);
        return ResponseEntity.ok(likes);
    }

    @Operation(summary = "Eliminar like", description = "Elimina un like específico de una publicación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Like eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Like no encontrado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @DeleteMapping("/posts/{postId}/likes/{likeId}")
    public ResponseEntity<Void> removeLike(
            @Parameter(description = "ID de la publicación", example = "1", required = true)
            @PathVariable @NotNull Long postId,
            @Parameter(description = "ID del like", example = "1", required = true)
            @PathVariable @NotNull Long likeId) {
        log.info("Eliminando like {} del post: {}", likeId, postId);
        service.removeLike(postId, likeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todos los usuarios", description = "Obtiene todos los usuarios del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios obtenidos exitosamente"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Obteniendo todos los usuarios");
        List<UserDto> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza la información de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable @NotNull Long id,
            @Parameter(description = "Datos actualizados del usuario", required = true)
            @Valid @RequestBody UpdateUserRequest request) {
        log.info("Actualizando usuario con ID: {}", id);
        UserDto user = service.updateUser(id, request);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "502", description = "Error en servicio dependiente")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID del usuario", example = "1", required = true)
            @PathVariable @NotNull Long id) {
        log.info("Eliminando usuario con ID: {}", id);
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
