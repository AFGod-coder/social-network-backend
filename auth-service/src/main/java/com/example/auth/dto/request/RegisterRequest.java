package com.example.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {
    @Email @NotBlank private String email;
    @NotBlank private String password;
    private String firstName;
    private String lastName;
    private String alias;
    private LocalDate dateOfBirth;
}
