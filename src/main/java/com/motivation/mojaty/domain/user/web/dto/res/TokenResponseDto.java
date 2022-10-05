package com.motivation.mojaty.domain.user.web.dto.res;

import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.Cookie;

@Getter
@Builder
public class TokenResponseDto {

    private Cookie accessToken;
    private Cookie refreshToken;

}
