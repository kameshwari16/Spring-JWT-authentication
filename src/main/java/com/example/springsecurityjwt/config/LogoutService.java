package com.example.springsecurityjwt.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.example.springsecurityjwt.Repositories.TokenRepository;
import com.example.springsecurityjwt.Services.RedisTokenService;
import com.example.springsecurityjwt.Services.impl.JWTServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{
    private final TokenRepository tokenRepository;
    //redis service
    private final RedisTokenService redisTokenService;
    private final JWTServiceImpl jwtServiceImpl;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // TODO Auto-generated method stub
        final String authHeader = request.getHeader("Authorization");
    final String jwt;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    jwt = authHeader.substring(7);
    //stored in database
    // var storedToken = tokenRepository.findByToken(jwt)
    //     .orElse(null);
    // if (storedToken != null) {
    //   storedToken.setExpired(true);
    //   storedToken.setRevoked(true);
    //   tokenRepository.save(storedToken);
    //   SecurityContextHolder.clearContext();
    // }


    //redis 
    String userEmail = jwtServiceImpl.extractUserName(jwt);
    System.out.println("User email is:"+userEmail);
    redisTokenService.deleteRefreshToken(userEmail);
    SecurityContextHolder.clearContext();
    }
}
