package com.motivation.mojaty.domain.user.web.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class UserWithdrawalRequestDto {

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "")
    private String password;
}
