package com.example.business.dto;

import lombok.Data;

@Data
class AuthRequest {
    private String email;
    private String password;
}