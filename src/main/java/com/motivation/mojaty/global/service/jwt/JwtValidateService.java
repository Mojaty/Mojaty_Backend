package com.motivation.mojaty.global.service.jwt;

import com.motivation.mojaty.global.exception.jwt.InvalidTokenException;
import com.motivation.mojaty.global.provider.jwt.JwtProvider;
import com.motivation.mojaty.global.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtValidateService {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    public String getEmail(String refreshToken) {
        return jwtProvider.extractAllClaims(refreshToken)
                .get("email", String.class);
    }

    public void validateRefreshToken(String refreshToken) {
        if (!redisService.getData(getEmail(refreshToken)).equals(refreshToken)) {
            throw InvalidTokenException.EXCEPTION;
        }
    }
}
