package com.motivation.mojaty.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;
import com.motivation.mojaty.domain.notification.domain.Notification;
import com.motivation.mojaty.domain.notification.domain.NotificationRepository;
import com.motivation.mojaty.domain.notification.web.dto.request.NotificationCreateRequestDto;
import com.motivation.mojaty.domain.notification.web.dto.request.FcmMessage;
import com.motivation.mojaty.domain.user.domain.User;
import com.motivation.mojaty.domain.user.domain.UserRepository;
import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;
import com.motivation.mojaty.global.provider.security.SecurityProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveNotification(NotificationCreateRequestDto req) {
        User user = userRepository.findByEmail(SecurityProvider.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RETRY_LOGIN));

        Notification notification = req.toEntity();

        notification.confirmUser(user);
        notificationRepository.save(notification);
    }

    public void sendNotification(FcmMessage req) throws ExecutionException, InterruptedException {
        Message message = Message.builder()
                .setToken(req.getMessage().getToken())
                .setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
                        .setNotification(new WebpushNotification(req.getMessage().getNotification().getTitle(), req.getMessage().getNotification().getBody()))
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().sendAsync(message).get();
        log.info(">>>>Send message : " + response);
    }

    public String getNotificationToken() {
        User user = userRepository.findByEmail(SecurityProvider.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RETRY_LOGIN));

        Notification notification = notificationRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return notification.getToken();
    }

    public void deleteNotification() {
        User user = userRepository.findByEmail(SecurityProvider.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RETRY_LOGIN));

        Notification notification = notificationRepository.findByUser(user)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        notificationRepository.delete(notification);
    }
}
