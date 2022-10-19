package com.motivation.mojaty.domain.motivation.web.dto.res;

import com.motivation.mojaty.domain.motivation.domain.Motivation;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MotivationResponseDto {

    private final Long id;
    private final String content;
    private final String motivationKind;
    private final String contentKind;
    private final String nickname;

    public MotivationResponseDto(Motivation motivation) {
        this.id = motivation.getId();
        this.content = motivation.getContent();
        this.motivationKind = motivation.getMotivationKind().name();
        this.contentKind = motivation.getContentKind().name();
        this.nickname = motivation.getUser().getNickname();
    }
}
