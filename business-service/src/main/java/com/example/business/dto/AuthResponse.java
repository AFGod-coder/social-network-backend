package com.example.business.dto;

import lombok.Data;

@Data
class AuthResponse { private String accessToken; private String refreshToken; private Long userId; }