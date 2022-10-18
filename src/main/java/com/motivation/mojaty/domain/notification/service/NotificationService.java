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

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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

    public String sendNotification(String token, String nickname) throws ExecutionException, InterruptedException {
        Message message = Message.builder()
                .setWebpushConfig(WebpushConfig.builder()
                        .setNotification(WebpushNotification.builder()
                                .setTitle("Mojaty")
                                .setBody(nickname + "님이 모티티를 등록하였습니다.")
                                .build())
                        .build())
                .putData("requestId", "https://www.youtube.com/") //테스트
                .setToken(token)
                .build();

        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    public List<String> getNotificationTokenAll() {
        User user = userRepository.findByEmail(SecurityProvider.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RETRY_LOGIN));

        return notificationRepository.findAll().stream()
                .map(Notification::getToken)
                .collect(Collectors.toList());
    }

    public void deleteNotification() {
        User user = userRepository.findByEmail(SecurityProvider.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RETRY_LOGIN));

        Notification notification = notificationRepository.findByUser(user)
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        notificationRepository.delete(notification);
    }

}
