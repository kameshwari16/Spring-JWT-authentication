package com.example.springsecurityjwt.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import com.example.springsecurityjwt.Entities.User;
import com.example.springsecurityjwt.Repositories.UserRepository;
import com.example.springsecurityjwt.Services.AuthenticationService;
import com.example.springsecurityjwt.Services.JWTService;
import com.example.springsecurityjwt.dto.JwtAuthenticationResponse;
import com.example.springsecurityjwt.dto.RefreshTokenRequest;
import com.example.springsecurityjwt.dto.SignInRequest;
import com.example.springsecurityjwt.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {
    // private final UserRepository userRepository;
    // private final User user;
    private final AuthenticationService authenticationService;
    private final JWTService jwtService;
    @PostMapping("auth/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
        //TODO: process POST request
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }
    @PostMapping("auth/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
        //TODO: process POST request
        System.out.println(signInRequest.getEmail());
        System.out.println(signInRequest.getPassword());
        // return null;
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }
    @PostMapping("auth/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        //TODO: process POST request
        // System.out.println(signInRequest.getEmail());
        // System.out.println(signInRequest.getPassword());
        // return null;
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}