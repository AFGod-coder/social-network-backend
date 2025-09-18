package com.example.auth.client;

import com.example.auth.dto.request.UserRequest;
import com.example.auth.dto.response.SocialUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "social-data-service",
        url = "${services.data:http://social-data-service:8081}"
)
public interface SocialDataClient {

    @PostMapping("/api/v1/users")
    SocialUserResponse createProfile(@RequestBody UserRequest request);
}
