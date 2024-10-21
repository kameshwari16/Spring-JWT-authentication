package com.example.springsecurityjwt.Services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.springsecurityjwt.dto.JwtAuthenticationResponse;
import com.example.springsecurityjwt.dto.RefreshTokenRequest;

import java.util.Map;
public interface JWTService {

    String extractUserName(String jwt);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token,UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
    // void invalidateToken(String token);

    // boolean isTokenInvalid(String token);

    // String getTokenFromAuthentication(UserDetails authentication);
}
