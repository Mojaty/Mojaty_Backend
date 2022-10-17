package com.motivation.mojaty.domain.motivation.web.dto.req;

import com.motivation.mojaty.domain.motivation.domain.Motivation;
import com.motivation.mojaty.domain.motivation.domain.type.ContentKind;
import com.motivation.mojaty.domain.motivation.domain.type.MotivationKind;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
public class MotivationImageRequestDto {

    private MotivationKind motivationKind;
    private ContentKind contentKind;
    private MultipartFile file;

    public Motivation toEntity() {
        return Motivation.builder()
                .motivationKind(motivationKind)
                .contentKind(contentKind)
                .build();
    }
}
