package com.motivation.mojaty.domain.user.web.dto.auth.res;

import lombok.*;

import javax.servlet.http.Cookie;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;

}
