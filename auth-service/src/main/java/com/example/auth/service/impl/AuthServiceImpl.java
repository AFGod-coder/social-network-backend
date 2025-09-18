package com.example.auth.service.impl;

import com.example.auth.client.SocialDataClient;
import com.example.auth.dto.request.LoginRequest;
import com.example.auth.dto.request.RegisterRequest;
import com.example.auth.dto.request.UserRequest;
import com.example.auth.dto.response.AuthResponse;
import com.example.auth.dto.response.SocialUserResponse;
import com.example.auth.entity.User;
import com.example.auth.exception.AliasAlreadyExistsException;
import com.example.auth.exception.EmailAlreadyExistsException;
import com.example.auth.exception.InvalidCredentialsException;
import com.example.auth.repository.UserRepository;
import com.example.auth.service.AuthService;
import com.example.auth.service.JwtService;
import com.example.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final SocialDataClient socialDataClient;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        if (userRepository.findByAlias(request.getAlias()).isPresent()) {
            throw new AliasAlreadyExistsException(request.getAlias());
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .alias(request.getAlias())
                .role("USER")
                .build();

        User savedUser = userRepository.save(user);

        UserRequest socialRequest = new UserRequest(
                request.getFirstName(),
                request.getLastName(),
                request.getAlias(),
                request.getEmail(),
                request.getDateOfBirth()
        );

        socialDataClient.createProfile(socialRequest);

        String access = jwtService.generateToken(savedUser);
        String refresh = refreshTokenService.createRefreshToken(savedUser).getToken();

        return new AuthResponse(access, refresh, savedUser.getId());
    }


    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String access = jwtService.generateToken(user);
        String refresh = refreshTokenService.createRefreshToken(user).getToken();

        return new AuthResponse(access, refresh, user.getId());
    }

}
