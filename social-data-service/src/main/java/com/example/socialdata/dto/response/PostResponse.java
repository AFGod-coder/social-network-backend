package com.example.socialdata.dto.response;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String message,
        Long authorId,
        String authorAlias,
        LocalDateTime createdAt,
        Integer likesCount
) {}
