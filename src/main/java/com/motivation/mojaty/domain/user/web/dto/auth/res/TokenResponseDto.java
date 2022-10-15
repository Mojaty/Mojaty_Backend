package com.motivation.mojaty.domain.user.web.dto.auth.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;

}
