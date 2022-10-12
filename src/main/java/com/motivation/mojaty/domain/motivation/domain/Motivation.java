package com.motivation.mojaty.domain.motivation.domain;

import com.motivation.mojaty.domain.motivation.domain.type.ContentKind;
import com.motivation.mojaty.domain.motivation.domain.type.MotivationKind;
import com.motivation.mojaty.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(STRING)
    private MotivationKind motivationKind; // 동기 부여 종류

    @Enumerated(STRING) // 컨텐츠 종류(영상, 사진)
    private ContentKind contentKind;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Motivation(String content, MotivationKind motivationKind, ContentKind contentKind) {
        this.content = content;
        this.motivationKind = motivationKind;
        this.contentKind = contentKind;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateKinds(MotivationKind motivationKind, ContentKind contentKind) {
        this.motivationKind = motivationKind;
        this.contentKind = contentKind;
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addMotivation(this);
    }
}
