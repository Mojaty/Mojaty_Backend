package com.motivation.mojaty.domain.user.web.dto.request;

import com.motivation.mojaty.domain.user.domain.User;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class UserJoinRequestDto {

    @NotNull(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "")
    private String password;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
