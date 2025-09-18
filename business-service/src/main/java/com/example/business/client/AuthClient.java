package com.example.business.client;

import com.example.business.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", url = "${services.auth}/api/v1")
public interface AuthClient {
    @GetMapping("/auth/users/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
}
