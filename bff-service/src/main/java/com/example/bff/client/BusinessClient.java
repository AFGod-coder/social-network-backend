package com.example.bff.client;

import com.example.bff.config.JwtFeignConfig;
import com.example.bff.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "business-service", url = "${services.business}", configuration = JwtFeignConfig.class)
public interface BusinessClient {

    @GetMapping("/api/v1/business/users/{id}")
    UserDto getUserById(@PathVariable Long id);

    @GetMapping("/api/v1/business/feed")
    List<PostDto> getFeed(@RequestParam Long userId);

    @PostMapping("/api/v1/business/posts")
    PostDto createPost(@RequestBody CreatePostRequest request);

    @PostMapping("/api/v1/business/posts/{postId}/likes")
    LikeDto addLike(@PathVariable Long postId, @RequestBody CreateLikeRequest request);

    @GetMapping("/api/v1/business/posts/{postId}/likes/count")
    Integer countLikes(@PathVariable Long postId);

    @GetMapping("/api/v1/business/posts/{id}")
    PostDto getPostById(@PathVariable Long id);

    @GetMapping("/api/v1/business/posts")
    List<PostDto> getAllPosts();

    @DeleteMapping("/api/v1/business/posts/{id}")
    void deletePost(@PathVariable Long id);

    @GetMapping("/api/v1/business/posts/{postId}/likes")
    List<LikeDto> getLikesByPost(@PathVariable Long postId);

    @DeleteMapping("/api/v1/business/posts/{postId}/likes/{likeId}")
    void removeLike(@PathVariable Long postId, @PathVariable Long likeId);

    @GetMapping("/api/v1/business/users")
    List<UserDto> getAllUsers();

    @PutMapping("/api/v1/business/users/{id}")
    UserDto updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request);

    @DeleteMapping("/api/v1/business/users/{id}")
    void deleteUser(@PathVariable Long id);
}
