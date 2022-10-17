package com.motivation.mojaty.domain.user.web.dto.user.req;

import com.motivation.mojaty.domain.user.domain.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserJoinRequestDto {

    private String email;
    private String nickname;
    private String phoneNumber;
    private String password;

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .password(password)
                .build();
    }
}
