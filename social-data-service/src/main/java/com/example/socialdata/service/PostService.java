package com.example.socialdata.service;

import com.example.socialdata.dto.request.CreatePostRequest;
import com.example.socialdata.dto.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(CreatePostRequest request);
    PostResponse getPostById(Long id);
    List<PostResponse> getAllPosts();
    void deletePost(Long id);
}
