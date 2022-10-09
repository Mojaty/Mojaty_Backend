package com.motivation.mojaty.domain.user.web.api.auth;

import com.motivation.mojaty.domain.notification.service.NotificationService;
import com.motivation.mojaty.domain.user.service.auth.AuthService;
import com.motivation.mojaty.domain.user.web.dto.auth.req.LoginRequestDto;
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
    private final NotificationService notificationService;

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto request) {
        return authService.login(request);
    }

    @PutMapping("/refresh")
    public TokenResponseDto getNewAccessToken(HttpServletRequest req) {
        return authService.getNewAccessToken(req);
    }
}
