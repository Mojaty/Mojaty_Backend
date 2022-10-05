package com.motivation.mojaty.domain.user.web.dto.user.req;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class UserUpdateRequestDto {

    @NotNull(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotNull(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 30)
    private String nickname;
}
