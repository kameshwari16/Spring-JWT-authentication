package com.example.springsecurityjwt.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    @GetMapping("/user")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from user only");
    }

}
