package com.motivation.mojaty.domain.notification.service;

import com.motivation.mojaty.domain.notification.web.dto.request.NotificationRequestDto;
import com.motivation.mojaty.domain.user.domain.User;
import com.motivation.mojaty.domain.user.domain.UserRepository;
import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;
import com.motivation.mojaty.global.provider.security.SecurityProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final UserRepository userRepository;

    private final String cron = "0 * * * * ?";

    @Value("${coolsms.api_key}")
    private String api_key;

    @Value("${coolsms.api_secret}")
    private String api_secret;

    @Value("${coolsms.phone_number}")
    private String phoneNumber;

    public void change(NotificationRequestDto req) {
        String cron = "0 " + req.getMinute() + " " + req.getHour() + " * * ?";
    }

    @Scheduled(cron = cron)
    public void send() {
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();

        User user = userRepository.findByEmail(SecurityProvider.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.RETRY_LOGIN));

        params.put("to", user.getPhoneNumber());
        params.put("from", phoneNumber);
        params.put("type", "sms");
        params.put("text", "[MOJATY] 공부할 시간입니다!");

        try {
            JSONObject obj = coolsms.send(params);
        } catch (CoolsmsException e) {
            log.error(e.getMessage());
        }
    }
}
