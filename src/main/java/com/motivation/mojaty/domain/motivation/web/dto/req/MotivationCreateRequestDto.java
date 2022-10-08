package com.motivation.mojaty.domain.motivation.web.dto.req;

import com.motivation.mojaty.domain.motivation.domain.Motivation;
import com.motivation.mojaty.domain.motivation.domain.type.ContentKind;
import com.motivation.mojaty.domain.motivation.domain.type.MotivationKind;
import lombok.Getter;

@Getter
public class MotivationCreateRequestDto {

    private MotivationKind motivationKind;
    private ContentKind contentKind;
    private String content;

    public Motivation toEntity() {
        return Motivation.builder()
                .motivationKind(motivationKind)
                .contentKind(contentKind)
                .content(content)
                .build();
    }
}
