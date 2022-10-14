package com.motivation.mojaty.domain.notification.service;

import com.motivation.mojaty.domain.notification.web.dto.request.FcmMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {

    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 5,11 * * *") // 새벽 5시와 밤 11시
    public void scheduledMessage() throws ExecutionException, InterruptedException {
        notificationService.sendNotification(makeMessage());
        log.info("보내졌습니다!");
    }

    private FcmMessage makeMessage() {
        return FcmMessage.builder()
                .token(notificationService.getNotificationToken())
                .title("Mojaty")
                .body("공부할 시간입니다!")
                .build();
    }
}
