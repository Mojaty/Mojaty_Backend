package com.motivation.mojaty.domain.user.web.dto.user.res;

import com.motivation.mojaty.domain.user.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String email;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
    }
}
