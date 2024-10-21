// package com.example.springsecurityjwt.Services.impl;

// import java.time.Instant;
// import java.util.Optional;
// import java.util.UUID;

// import org.springframework.stereotype.Service;

// import com.example.springsecurityjwt.Entities.RefreshToken;
// import com.example.springsecurityjwt.Repositories.RefreshTokenRepository;
// import com.example.springsecurityjwt.Repositories.UserRepository;

// import lombok.RequiredArgsConstructor;

// @Service
// @RequiredArgsConstructor
// public class RefreshTokenService {

//     private final RefreshTokenRepository refreshTokenRepository;
//     private final UserRepository userRepository;

//     public Optional<RefreshToken> findByToken(String token) {
//         return refreshTokenRepository.findByToken(token);
//     }

//     public RefreshToken createRefreshToken(Long userId) {
//         RefreshToken refreshToken = new RefreshToken();
//         refreshToken.setUser(userRepository.findById(userId).orElseThrow());
//         refreshToken.setExpiryDate(Instant.now().plusMillis(604800000)); // 7 days expiry
//         refreshToken.setToken(UUID.randomUUID().toString());

//         return refreshTokenRepository.save(refreshToken);
//     }

//     public void deleteByUserId(Long userId) {
//         refreshTokenRepository.deleteByUserId(userId);
//     }

//     public boolean isTokenExpired(RefreshToken token) {
//         return token.getExpiryDate().isBefore(Instant.now());
//     }
// }


package com.example.springsecurityjwt.Services.impl;

import com.example.springsecurityjwt.Services.RedisTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTokenService redisTokenService;

    public boolean validateRefreshToken(String tokenId, String refreshToken) {
        String storedToken = redisTokenService.getRefreshToken(tokenId);
        return storedToken != null && storedToken.equals(refreshToken);
    }
}
