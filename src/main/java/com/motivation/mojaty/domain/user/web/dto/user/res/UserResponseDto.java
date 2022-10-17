package com.motivation.mojaty.domain.user.web.dto.user.res;

import com.motivation.mojaty.domain.user.domain.User;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserResponseDto {

    private final String email;
    private final String nickname;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
