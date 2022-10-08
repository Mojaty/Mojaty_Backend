package com.motivation.mojaty.domain.notification.service;

import com.motivation.mojaty.domain.notification.web.dto.request.NotificationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class ScheduledService {

    private final NotificationService notificationService;

    @Scheduled(cron = "0 30 7 * * ?")
    public void scheduledSend() throws ExecutionException, InterruptedException {
        NotificationRequestDto notificationRequestDto = NotificationRequestDto.builder()
                .title("Mojaty")
                .token(notificationService.getNotificationToken())
                .message("공부할 시간입니다!")
                .build();
        notificationService.sendNotification(notificationRequestDto);
    }
}
