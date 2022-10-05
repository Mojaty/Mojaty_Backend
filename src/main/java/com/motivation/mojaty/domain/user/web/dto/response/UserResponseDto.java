package com.motivation.mojaty.domain.user.web.dto.response;

import com.motivation.mojaty.domain.user.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String email;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
    }
}
