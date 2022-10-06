package com.motivation.mojaty.domain.user.web.api.auth;

import com.motivation.mojaty.domain.user.service.auth.AuthService;
import com.motivation.mojaty.domain.user.web.dto.auth.req.LoginRequestDto;
import com.motivation.mojaty.domain.user.web.dto.auth.res.LogoutResponseDto;
import com.motivation.mojaty.domain.user.web.dto.auth.res.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto request, HttpServletResponse res) {
        TokenResponseDto tokenRes = authService.login(request);

        res.addCookie(tokenRes.getAccessToken());
        res.addCookie(tokenRes.getRefreshToken());

        return tokenRes;
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        LogoutResponseDto dto = authService.logout(req);
        res.addCookie(dto.getAccessToken());
        res.addCookie(dto.getRefreshToken());
    }

    @PutMapping("/refresh")
    public TokenResponseDto getNewAccessToken(HttpServletRequest req, HttpServletResponse res) {
        TokenResponseDto tokenRes = authService.getNewAccessToken(req);

        res.addCookie(tokenRes.getBeforeAccessToken());
        res.addCookie(tokenRes.getAccessToken());

        return tokenRes;
    }
}
