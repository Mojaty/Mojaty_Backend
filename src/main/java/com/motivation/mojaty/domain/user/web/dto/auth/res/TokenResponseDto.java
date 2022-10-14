package com.motivation.mojaty.domain.user.web.dto.auth.res;

import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.Cookie;

@Getter
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;

}
