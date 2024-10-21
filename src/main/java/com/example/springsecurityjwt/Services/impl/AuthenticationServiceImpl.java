// package com.example.springsecurityjwt.Services.impl;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.example.springsecurityjwt.Entities.Role;
// import com.example.springsecurityjwt.Entities.User;
// import com.example.springsecurityjwt.Repositories.UserRepository;
// import com.example.springsecurityjwt.Services.AuthenticationService;
// import com.example.springsecurityjwt.Services.JWTService;
// import com.example.springsecurityjwt.dto.JwtAuthenticationResponse;
// import com.example.springsecurityjwt.dto.SignInRequest;
// import com.example.springsecurityjwt.dto.SignUpRequest;
// import com.example.springsecurityjwt.dto.RefreshTokenRequest;
// import java.util.Optional;
// import lombok.RequiredArgsConstructor;
// import java.util.HashMap;
// @Service
// @RequiredArgsConstructor
// public class AuthenticationServiceImpl implements AuthenticationService {
//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final AuthenticationManager authenticationManager;
//     private final JWTService jwtService;
//     // private final RefreshTokenService refreshTokenService;
//     public User signup(SignUpRequest signUpRequest){
//         User user=new User();
//         user.setEmail(signUpRequest.getEmail());
//         user.setfirstname(signUpRequest.getFirstname());
//         user.setlastname(signUpRequest.getLastname());
//         // user.setRole(Role.USER);
//         // Set the role based on the input, defaults to USER if an invalid role is provided
//         String roleInput = signUpRequest.getRole().toUpperCase();
//         if (roleInput.equals("ADMIN")) {
//                 user.setRole(Role.ADMIN);
//         } else {
//             user.setRole(Role.USER);
//         }
        
//         user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//         Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//         if (existingUser.isPresent()) {
//             // ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
//             throw new IllegalArgumentException("Email already in use"); // Throw an exception if email exists

//         }
//         return userRepository.save(user);
//     }
//     public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
//         // try {
//         //     System.out.println("AUTHENTICATE");
//         //     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
//         // } catch (Exception e) {
//         //     throw new IllegalArgumentException("Invalid email or password"); // Handle invalid credentials
//         // }
//         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
//         User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));
//         String jwt = jwtService.generateToken(user);
//         //refresh token
//         String refreshToken=jwtService.generateRefreshToken(new HashMap<>(),user);//Map gives you flexibility if, later on, you want to add more information/claims into the JWT without changing the method signature.
//         JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
//         jwtAuthenticationResponse.setToken(jwt);
//         jwtAuthenticationResponse.setRefreshToken(refreshToken);
//         System.out.println("JWT Token: " + jwt); // Add this line to check token generation
//         return jwtAuthenticationResponse;
//     }
//     public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
//         String userEmail=jwtService.extractUserName(refreshTokenRequest.getToken());
//         User user=userRepository.findByEmail(userEmail).orElseThrow();
//         if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
//             var jwt=jwtService.generateToken(user);
//             JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
//             jwtAuthenticationResponse.setToken(jwt);
//             jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
//             System.out.println("After token is refreshed:" + jwt);
//             return jwtAuthenticationResponse;
//         }
//         return null;
//     }
// }




package com.example.springsecurityjwt.Services.impl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springsecurityjwt.Entities.Role;
import com.example.springsecurityjwt.Entities.TokenType;
import com.example.springsecurityjwt.Entities.Token;
import com.example.springsecurityjwt.Entities.User;
import com.example.springsecurityjwt.Repositories.UserRepository;
import com.example.springsecurityjwt.Repositories.TokenRepository;
import com.example.springsecurityjwt.Services.AuthenticationService;
import com.example.springsecurityjwt.Services.JWTService;
import com.example.springsecurityjwt.Services.RedisTokenService;
import com.example.springsecurityjwt.dto.JwtAuthenticationResponse;
import com.example.springsecurityjwt.dto.SignInRequest;
import com.example.springsecurityjwt.dto.SignUpRequest;
import com.example.springsecurityjwt.dto.RefreshTokenRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    // private final RefreshTokenService refreshTokenService;
    //redis
    private final RedisTokenService redisTokenService;
    public User signup(SignUpRequest signUpRequest){
        User user=new User();
        user.setEmail(signUpRequest.getEmail());
        user.setfirstname(signUpRequest.getFirstname());
        user.setlastname(signUpRequest.getLastname());
        // user.setRole(Role.USER);
        // Set the role based on the input, defaults to USER if an invalid role is provided
        String roleInput = signUpRequest.getRole().toUpperCase();
        if (roleInput.equals("ADMIN")) {
                user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            // ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
            throw new IllegalArgumentException("Email already in use"); // Throw an exception if email exists

        }
        // User user1 = userRepository.findByEmail(signUpRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return userRepository.save(user);
    }
    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        // try {
        //     System.out.println("AUTHENTICATE");
        //     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        // } catch (Exception e) {
        //     throw new IllegalArgumentException("Invalid email or password"); // Handle invalid credentials
        // }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        String jwt = jwtService.generateToken(user);
        //refresh token
        String refreshToken=jwtService.generateRefreshToken(new HashMap<>(),user);//Map gives you flexibility if, later on, you want to add more information/claims into the JWT without changing the method signature.
        //redis
        saveRefreshToken(user.getEmail(), refreshToken);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        revokeAllUserTokens(user);//to revoke the previos tokens of the user
        saveUserToken(user, jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        System.out.println("JWT Token: " + jwt); // Add this line to check token generation
        return jwtAuthenticationResponse;
    }
    private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }
  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail=jwtService.extractUserName(refreshTokenRequest.getToken());
        User user=userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt=jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            System.out.println("After token is refreshed:" + jwt);
            return jwtAuthenticationResponse;
        }
        return null;
    }
    //redis
    public void saveRefreshToken(String userEmail, String refreshToken) {
      // Save the refresh token in Redis using the user's email as the key
      redisTokenService.storeRefreshToken(userEmail, refreshToken);
  }
  
}
