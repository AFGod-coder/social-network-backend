package com.example.socialdata.service;

import com.example.socialdata.dto.request.CreateUserRequest;
import com.example.socialdata.dto.request.UpdateUserRequest;
import com.example.socialdata.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UpdateUserRequest request);
    void deleteUser(Long id);
}
