package com.motivation.mojaty.domain.user.web.dto.user.req;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@ToString
public class UserWithdrawalRequestDto {

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{6,20}",
            message = "영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 6자 ~ 20자여야 합니다.")
    private String password;
}
