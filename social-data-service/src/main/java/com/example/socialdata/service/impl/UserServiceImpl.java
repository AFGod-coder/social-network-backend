package com.example.socialdata.service.impl;

import com.example.socialdata.dto.request.CreateUserRequest;
import com.example.socialdata.dto.request.UpdateUserRequest;
import com.example.socialdata.dto.response.UserResponse;
import com.example.socialdata.entity.UserEntity;
import com.example.socialdata.exception.UserException.DuplicateAliasException;
import com.example.socialdata.exception.UserException.DuplicateEmailException;
import com.example.socialdata.exception.UserException.UserNotFoundException;
import com.example.socialdata.repository.UserRepository;
import com.example.socialdata.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (request.getFirstName() == null || request.getFirstName().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        if (request.getLastName() == null || request.getLastName().isBlank())
            throw new IllegalArgumentException("El apellido es obligatorio");
        if (request.getEmail() == null || request.getEmail().isBlank())
            throw new IllegalArgumentException("El email es obligatorio");
        if (request.getAlias() == null || request.getAlias().isBlank())
            throw new IllegalArgumentException("El alias es obligatorio");

        if (userRepository.existsByEmail(request.getEmail()))
            throw new DuplicateEmailException(request.getEmail());
        if (userRepository.existsByAlias(request.getAlias()))
            throw new DuplicateAliasException(request.getAlias());

        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .alias(request.getAlias())
                .email(request.getEmail())
                .role("USER")
                .dateOfBirth(request.getDateOfBirth())
                .build();
        userRepository.save(user);
        return map(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::map)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (request.getAlias() != null && !request.getAlias().equals(user.getAlias()) && userRepository.existsByAlias(request.getAlias()))
            throw new DuplicateAliasException(request.getAlias());

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getAlias() != null) user.setAlias(request.getAlias());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getDateOfBirth() != null) user.setDateOfBirth(request.getDateOfBirth());

        userRepository.save(user);
        return map(user);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }

    private UserResponse map(UserEntity u) {
        return new UserResponse(
                u.getId(),
                u.getFirstName(),
                u.getLastName(),
                u.getAlias(),
                u.getEmail(),
                u.getRole(),
                u.getDateOfBirth(),
                u.getCreatedAt()
        );
    }
}
