package com.motivation.mojaty.domain.user.web.api;

import com.motivation.mojaty.domain.user.service.AuthService;
import com.motivation.mojaty.domain.user.web.dto.req.LoginRequestDto;
import com.motivation.mojaty.domain.user.web.dto.res.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponseDto login(
            @RequestBody @Valid LoginRequestDto request,
            HttpServletResponse res
    ) {
        TokenResponseDto tokenRes = authService.login(request);

        res.addCookie(tokenRes.getAccessToken());
        res.addCookie(tokenRes.getRefreshToken());

        return tokenRes;
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest req) {
        authService.logout(req.getHeader("ACCESS-TOKEN"));
    }

    @PutMapping("/refresh")
    public TokenResponseDto getNewAccessToken(
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        TokenResponseDto tokenRes = authService.getNewAccessToken(req.getHeader("REFRESH-TOKEN"));

        res.addCookie(tokenRes.getAccessToken());
        res.addCookie(tokenRes.getRefreshToken());

        return tokenRes;
    }
}
