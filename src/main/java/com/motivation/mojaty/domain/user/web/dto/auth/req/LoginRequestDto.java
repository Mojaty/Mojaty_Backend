package com.motivation.mojaty.domain.user.web.dto.auth.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @NotNull(message = "이메일을 입력해 주세요.")
    @Email
    private String email;

    @NotNull(message = "비밀번호를 입력해 주세요.")
    private String password;
}
