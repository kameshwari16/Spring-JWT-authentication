package com.example.springsecurityjwt.Services;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
@Service
public class RedisTokenService {
     private static final long REFRESH_TOKEN_EXPIRATION = 7; // Expiry in days

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void storeRefreshToken(String userEmail, String refreshToken) {
        redisTemplate.opsForValue().set(userEmail, refreshToken, REFRESH_TOKEN_EXPIRATION, TimeUnit.DAYS);
    }

    public String getRefreshToken(String userEmail) {
        return redisTemplate.opsForValue().get(userEmail);
    }

    public void deleteRefreshToken(String userEmail) {
        redisTemplate.delete(userEmail);
    }
}
