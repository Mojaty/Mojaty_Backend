package com.motivation.mojaty.domain.user.web.dto.auth.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class LoginRequestDto {

    private String email;

    private String password;
}
