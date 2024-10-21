package com.example.springsecurityjwt.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;

}
