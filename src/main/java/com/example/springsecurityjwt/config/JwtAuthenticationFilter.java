package com.example.springsecurityjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springsecurityjwt.Repositories.TokenRepository;
import com.example.springsecurityjwt.Services.JWTService;
import com.example.springsecurityjwt.Services.RedisTokenService;
import com.example.springsecurityjwt.Services.UserService;
import com.example.springsecurityjwt.Services.impl.UserServiceImpl;

import io.jsonwebtoken.io.IOException;
import io.micrometer.common.lang.NonNull;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JWTService jwtservice;
    private final UserServiceImpl userServiceImpl;
    private final TokenRepository tokenRepository;
    //redis 
    private final RedisTokenService redisTokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)throws ServletException,IOException, java.io.IOException{
        
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (StringUtils.isEmpty(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt=authHeader.substring(7);//The JWT token is obtained by removing the "Bearer " prefix (7 characters).
        System.out.println("JWT extracted: " + jwt);
        userEmail=jwtservice.extractUserName(jwt);
        System.out.println("Extracted User Email from JWT: " + userEmail);
        //authenticating user
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails=userServiceImpl.loadUserByUsername(userEmail);

            
            //validating token in db
            // var isTokenValid = tokenRepository.findByToken(jwt)//authenticating also based on expired/revoked status of token
            // .map(t -> !t.isExpired() && !t.isRevoked())
            // .orElse(false);


            //validation in redis check if refresh token exists in Redis
            boolean isTokenValid=redisTokenService.getRefreshToken(userEmail) != null;
            if(jwtservice.isTokenValid(jwt, userDetails) && isTokenValid ){//token validation
                // SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
                System.out.println("Validating token for user: " + userEmail);
                boolean isValid = jwtservice.isTokenValid(jwt, userDetails);
                System.out.println("Token is valid: " + isValid);

                UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userDetails, null ,userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
            else {
                System.out.println("Invalid JWT Token");
            }
        }
        filterChain.doFilter(request, response);
    }
}
