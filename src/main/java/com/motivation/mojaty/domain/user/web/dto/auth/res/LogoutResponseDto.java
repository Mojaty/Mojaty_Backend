package com.motivation.mojaty.domain.user.web.dto.auth.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.servlet.http.Cookie;

@Getter
@NoArgsConstructor
@ToString
public class LogoutResponseDto {

    private Cookie accessToken;
    private Cookie refreshToken;

    @Builder
    public LogoutResponseDto(Cookie accessToken, Cookie refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
