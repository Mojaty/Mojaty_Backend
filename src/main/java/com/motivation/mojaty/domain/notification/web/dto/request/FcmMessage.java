package com.motivation.mojaty.domain.notification.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class FcmMessage {

    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private String token;
        private Notification notification;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
    }
}
