package com.motivation.mojaty.global.provider.jwt;

import com.motivation.mojaty.global.exception.jwt.ExpiredTokenException;
import com.motivation.mojaty.global.exception.jwt.InvalidTokenException;
import com.motivation.mojaty.global.service.redis.RedisService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

import static com.motivation.mojaty.global.provider.jwt.JwtProperties.JWT_HEADER;
import static com.motivation.mojaty.global.provider.jwt.JwtProperties.JWT_PREFIX;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final RedisService redisService;

    @Value("${spring.security.jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.security.jwt.blacklist.access-token}")
    private String BLACKLIST_AT_PREFIX;

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
        return createJwt(email, JwtProperties.ACCESS_TOKEN_VALID_TIME);
    }

    public String createRefreshToken(String email) {
        return createJwt(email, JwtProperties.REFRESH_TOKEN_VALID_TIME);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(JWT_HEADER);
        return parseToken(bearer);
    }

    public String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(JWT_PREFIX))
            return bearerToken.replace(JWT_PREFIX, "");
        return null;
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
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

    public void logout(String email, String accessToken) {
        long expiredAccessTokenTime = getExpiredTime(accessToken)
                .getTime() - new Date().getTime();

        redisService.setValues(BLACKLIST_AT_PREFIX + accessToken, email,
                Duration.ofMillis(expiredAccessTokenTime));
        redisService.deleteData(email);

        redisService.setBlackList(accessToken, "ACCESS-TOKEN", expiredAccessTokenTime);
    }

    private Date getExpiredTime(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody().getExpiration();
    }

}
