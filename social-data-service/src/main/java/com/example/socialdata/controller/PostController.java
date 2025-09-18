package com.example.socialdata.controller;

import com.example.socialdata.dto.request.CreatePostRequest;
import com.example.socialdata.dto.response.PostResponse;
import com.example.socialdata.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "Endpoints para manejo de publicaciones")
public class PostController {
    private final PostService postService;

    @Operation(summary = "Crear publicación")
    @ApiResponse(responseCode = "201", description = "Publicación creada exitosamente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Valid @RequestBody CreatePostRequest request) {
        return postService.createPost(request);
    }

    @Operation(summary = "Obtener publicación por ID")
    @GetMapping("/{id}")
    public PostResponse getById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @Operation(summary = "Listar todas las publicaciones")
    @GetMapping
    public List<PostResponse> getAll() {
        return postService.getAllPosts();
    }

    @Operation(summary = "Eliminar publicación por ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
