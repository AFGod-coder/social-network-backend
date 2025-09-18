package com.example.bff.client;

import com.example.bff.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service", url = "${services.auth}")
public interface AuthClient {

    @PostMapping("/api/v1/auth/login")
    AuthResponse login(@RequestBody AuthRequest request);

    @PostMapping("/api/v1/auth/register")
    AuthResponse register(@RequestBody RegisterRequest request);

    @GetMapping("/api/v1/auth/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);

    @PostMapping("/api/v1/auth/refresh")
    AuthResponse refreshToken(@RequestBody RefreshTokenRequest request);
}
