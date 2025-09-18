package com.example.socialdata.controller;

import com.example.socialdata.dto.request.CreateLikeRequest;
import com.example.socialdata.dto.response.LikeResponse;
import com.example.socialdata.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
@RequiredArgsConstructor
@Tag(name = "Likes", description = "Endpoints para manejo de likes")
public class LikeController {
    private final LikeService likeService;

    @Operation(summary = "Agregar like a publicación")
    @ApiResponse(responseCode = "201", description = "Like agregado exitosamente")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LikeResponse like(@PathVariable Long postId, @Valid @RequestBody CreateLikeRequest request) {
        return likeService.addLike(postId, request);
    }

    @Operation(summary = "Listar likes de una publicación")
    @GetMapping
    public List<LikeResponse> list(@PathVariable Long postId) {
        return likeService.getLikesByPost(postId);
    }

    @Operation(summary = "Contar likes de una publicación")
    @GetMapping("/count")
    public Integer count(@PathVariable Long postId) {
        return likeService.countLikes(postId);
    }

    @Operation(summary = "Remover like de una publicación")
    @DeleteMapping("/{likeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@PathVariable("likeId") Long likeId) {
        likeService.removeLike(likeId);
    }
}
