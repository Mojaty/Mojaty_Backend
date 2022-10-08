package com.motivation.mojaty.domain.user.service.auth;

import com.motivation.mojaty.domain.user.domain.User;
import com.motivation.mojaty.domain.user.domain.UserRepository;
import com.motivation.mojaty.domain.user.web.dto.auth.req.LoginRequestDto;
import com.motivation.mojaty.domain.user.web.dto.auth.res.LogoutResponseDto;
import com.motivation.mojaty.domain.user.web.dto.auth.res.TokenResponseDto;
import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;
import com.motivation.mojaty.global.provider.cookie.CookieProvider;
import com.motivation.mojaty.global.provider.jwt.JwtProvider;
import com.motivation.mojaty.global.provider.security.SecurityProvider;
import com.motivation.mojaty.global.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static com.motivation.mojaty.global.provider.jwt.JwtProperties.ACCESS_TOKEN_VALID_TIME;
import static com.motivation.mojaty.global.provider.jwt.JwtProperties.REFRESH_TOKEN_VALID_TIME;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    private final CookieProvider cookieProvider;

    public TokenResponseDto login(LoginRequestDto loginReq) {
        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.matchedPassword(passwordEncoder, user, loginReq.getPassword());

        final String accessToken = jwtProvider.createAccessToken(loginReq.getEmail());
        final String refreshToken = jwtProvider.createRefreshToken(loginReq.getEmail());
        redisService.setDataExpire(loginReq.getEmail(), refreshToken, REFRESH_TOKEN_VALID_TIME);

        Cookie accessTokenCookie = cookieProvider.createCookie("ACCESS-TOKEN", accessToken);
        Cookie refreshTokenCookie = cookieProvider.createCookie("REFRESH-TOKEN", refreshToken);

        return TokenResponseDto.builder()
                .accessToken(accessTokenCookie)
                .refreshToken(refreshTokenCookie)
                .build();
    }

    public LogoutResponseDto logout(HttpServletRequest req) {
        Cookie accessToken = jwtProvider.resolveToken(req);
        Cookie refreshToken = jwtProvider.resolveRefreshToken(req);

        expireCookie(accessToken);
        expireCookie(refreshToken);

        return LogoutResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponseDto getNewAccessToken(HttpServletRequest req) {
        String refreshToken = jwtProvider.resolveRefreshToken(req).getValue();
        Cookie cookie = jwtProvider.resolveToken(req);
        jwtProvider.validateRefreshToken(refreshToken);
        jwtProvider.checkRefreshToken(refreshToken);
        expireCookie(cookie);

        String accessToken = jwtProvider.createAccessToken(jwtProvider.getEmail(refreshToken));

        Cookie accessTokenCookie = cookieProvider.createCookie("ACCESS-TOKEN", accessToken);
        return TokenResponseDto.builder()
                .accessToken(accessTokenCookie)
                .beforeAccessToken(cookie)
                .build();
    }

    private void expireCookie(Cookie cookie) {
        cookie.setMaxAge(0);
        cookie.setPath("/");
    }
}
