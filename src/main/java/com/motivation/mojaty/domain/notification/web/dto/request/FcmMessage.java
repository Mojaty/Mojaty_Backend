package com.motivation.mojaty.domain.notification.web.dto.request;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class FcmMessage {

    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}
