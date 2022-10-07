package com.motivation.mojaty.global.provider.jwt;

import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;
import com.motivation.mojaty.global.exception.jwt.ExpiredTokenException;
import com.motivation.mojaty.global.exception.jwt.InvalidTokenException;
import com.motivation.mojaty.global.service.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.motivation.mojaty.global.provider.jwt.JwtProperties.ACCESS_TOKEN_VALID_TIME;
import static com.motivation.mojaty.global.provider.jwt.JwtProperties.JWT_HEADER;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtProvider {

    private final RedisService redisService;

    @Value("${spring.security.jwt.secret}")
    private String SECRET_KEY;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createJwt(String email, Long time) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + time))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAccessToken(String email) {
        return createJwt(email, ACCESS_TOKEN_VALID_TIME);
    }

    public String createRefreshToken(String email) {
        return createJwt(email, JwtProperties.REFRESH_TOKEN_VALID_TIME);
    }

    public Cookie resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        checkCookie(cookies);
        return getCookieByJwtName(cookies, JWT_HEADER);
    }

    public Cookie resolveRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        checkCookie(cookies);
        return getCookieByJwtName(cookies, "REFRESH-TOKEN");
    }

    public void checkCookie(Cookie[] cookies) {
        if(cookies == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public Cookie getCookieByJwtName(Cookie[] cookies, String headerName) {
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(headerName)) {
                return cookie;
            }
        }
        return null;
    }

    public String getEmail(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody().get("email", String.class);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    public void validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(SECRET_KEY))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    public void checkRefreshToken(String token) {
        if(!redisService.getData(getEmail(token)).equals(token)) {
            throw InvalidTokenException.EXCEPTION;
        }
    }
}
