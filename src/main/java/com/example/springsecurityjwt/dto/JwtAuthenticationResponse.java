package com.example.springsecurityjwt.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String RefreshToken;
}