package com.example.springsecurityjwt.Services;

import org.springframework.http.ResponseEntity;

import com.example.springsecurityjwt.Entities.User;
import com.example.springsecurityjwt.dto.JwtAuthenticationResponse;
import com.example.springsecurityjwt.dto.RefreshTokenRequest;
import com.example.springsecurityjwt.dto.SignInRequest;
import com.example.springsecurityjwt.dto.SignUpRequest;

public interface AuthenticationService {
     public User signup(SignUpRequest signUpRequest);
     public JwtAuthenticationResponse signin(SignInRequest signInRequest);
     public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}

