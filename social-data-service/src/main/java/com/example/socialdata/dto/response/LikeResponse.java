package com.example.socialdata.dto.response;

import java.time.LocalDateTime;

public record LikeResponse(
        Long id,
        Long userId,
        Long postId,
        LocalDateTime createdAt
) {}
