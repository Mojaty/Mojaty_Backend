package com.motivation.mojaty.domain.notification.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class FcmMessage {

    private String token;
    private String title;
    private String body;
}
