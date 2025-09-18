package com.example.auth.service;

import com.example.auth.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(Long id);
}
