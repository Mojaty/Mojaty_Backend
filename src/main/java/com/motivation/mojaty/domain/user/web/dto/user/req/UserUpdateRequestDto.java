package com.motivation.mojaty.domain.user.web.dto.user.req;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class UserUpdateRequestDto {

    @NotNull(message = "이메일을 입력해주세요.")
    @Email
    private String email;
}
