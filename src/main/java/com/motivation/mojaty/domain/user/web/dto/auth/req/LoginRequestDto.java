package com.motivation.mojaty.domain.user.web.dto.auth.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    private final String email;

    private final  String password;
}
