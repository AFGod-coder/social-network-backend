package com.example.business.dto;

import lombok.Data;

@Data
class RegisterRequest { private String email, password, firstName, lastName, alias; }