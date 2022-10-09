package com.motivation.mojaty.domain.notification.web.dto.request;

import com.motivation.mojaty.domain.notification.domain.Notification;
import lombok.Getter;

@Getter
public class NotificationCreateRequestDto {

    private String token;

    public Notification toEntity() {
        return Notification.builder()
                .token(token)
                .build();
    }
}
