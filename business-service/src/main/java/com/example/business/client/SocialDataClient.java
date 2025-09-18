package com.example.business.client;

import com.example.business.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "social-data-service", url = "${services.data}")
public interface SocialDataClient {

    @GetMapping("/api/v1/posts")
    List<PostDto> getAllPosts();

    @PostMapping("/api/v1/posts")
    PostDto createPost(@RequestBody CreatePostRequest request);

    @GetMapping("/api/v1/posts/{id}")
    PostDto getPostById(@PathVariable("id") Long id);

    @PostMapping("/api/v1/posts/{postId}/likes")
    LikeDto addLike(@PathVariable("postId") Long postId, @RequestBody CreateLikeRequest request);

    @GetMapping("/api/v1/posts/{postId}/likes")
    List<LikeDto> getLikes(@PathVariable("postId") Long postId);

    @GetMapping("/api/v1/posts/{postId}/likes/count")
    Integer countLikes(@PathVariable("postId") Long postId);

    @GetMapping("/api/v1/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

    @DeleteMapping("/api/v1/posts/{id}")
    void deletePost(@PathVariable("id") Long id);

    @DeleteMapping("/api/v1/posts/{postId}/likes/{likeId}")
    void removeLike(@PathVariable("likeId") Long likeId);

    @GetMapping("/api/v1/users")
    List<UserDto> getAllUsers();

    @PutMapping("/api/v1/users/{id}")
    UserDto updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserRequest request);

    @DeleteMapping("/api/v1/users/{id}")
    void deleteUser(@PathVariable("id") Long id);
}
