package com.motivation.mojaty.domain.user.web.dto.auth.req;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LoginRequestDto {

    private String email;

    private String password;
}
