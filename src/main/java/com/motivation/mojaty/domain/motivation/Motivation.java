package com.motivation.mojaty.domain.motivation;

import com.motivation.mojaty.domain.motivation.type.ContentKind;
import com.motivation.mojaty.domain.motivation.type.MotivationKind;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Motivation {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Enumerated(STRING)
    private MotivationKind motivationKind; // 동기 부여 종류

    @Enumerated(STRING) // 컨텐츠 종류(영상, 사진)
    private ContentKind contentKind;

}
