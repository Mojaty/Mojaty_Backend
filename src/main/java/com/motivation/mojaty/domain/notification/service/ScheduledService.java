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

    @Scheduled(cron = "0 52 1 * * ?")
    public void scheduledSend() throws ExecutionException, InterruptedException {
        log.info(">>>>>>>메세지 보내기 시작");
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(notificationService.getNotificationToken())
                        .notification(FcmMessage.Notification.builder()
                                .title("Mojaty")
                                .body("공부할 시간입니다!")
                                .build())
                        .build())
                .build();
        notificationService.sendNotification(fcmMessage);
        log.info("보내졌습니다!");
    }
}
