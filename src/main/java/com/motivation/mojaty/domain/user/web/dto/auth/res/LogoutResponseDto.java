package com.motivation.mojaty.domain.user.web.dto.auth.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;

@Getter
@NoArgsConstructor
public class LogoutResponseDto {

    private Cookie accessToken;
    private Cookie refreshToken;

    @Builder
    public LogoutResponseDto(Cookie accessToken, Cookie refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
