package com.motivation.mojaty.domain.motivation.web.dto.res;

import com.motivation.mojaty.domain.motivation.domain.Motivation;
import lombok.Getter;

@Getter
public class MotivationResponseDto {

    private final String content;

    public MotivationResponseDto(Motivation motivation) {
        this.content = motivation.getContent();
    }
}
