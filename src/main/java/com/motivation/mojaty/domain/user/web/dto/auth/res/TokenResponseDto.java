package com.motivation.mojaty.domain.user.web.dto.auth.res;

import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.Cookie;

@Getter
@Builder
public class TokenResponseDto {

    private Cookie accessToken;
    private Cookie beforeAccessToken;
    private Cookie refreshToken;

}
