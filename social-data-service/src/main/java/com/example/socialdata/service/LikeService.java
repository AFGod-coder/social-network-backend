package com.example.socialdata.service;

import com.example.socialdata.dto.request.CreateLikeRequest;
import com.example.socialdata.dto.response.LikeResponse;

import java.util.List;

public interface LikeService {
    LikeResponse addLike(Long postId, CreateLikeRequest request);
    void removeLike(Long id);
    List<LikeResponse> getLikesByPost(Long postId);
    Integer countLikes(Long postId);
}
