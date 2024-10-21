package com.example.springsecurityjwt.dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;

}
