package com.example.auth.service.impl;

import com.example.auth.dto.response.UserResponse;
import com.example.auth.entity.User;
import com.example.auth.exception.UserNotFoundException;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getAlias(),
                user.getRole(),
                LocalDateTime.ofInstant(user.getCreatedAt(), ZoneId.systemDefault())
        );
    }
}
