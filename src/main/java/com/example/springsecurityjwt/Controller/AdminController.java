package com.example.springsecurityjwt.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurityjwt.Entities.User;
import com.example.springsecurityjwt.Services.AuthenticationService;
import com.example.springsecurityjwt.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AdminController {
    @GetMapping("/admin")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Hello from admin only");
    }
}
