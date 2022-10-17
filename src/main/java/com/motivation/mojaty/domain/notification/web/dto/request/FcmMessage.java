package com.motivation.mojaty.domain.notification.web.dto.request;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class FcmMessage {

    private String token;
    private String title;
    private String body;
}
