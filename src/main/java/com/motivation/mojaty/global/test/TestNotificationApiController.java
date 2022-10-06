package com.motivation.mojaty.global.test;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/notification")
@Slf4j
public class TestNotificationApiController {

    @Value("${coolsms.api_key}")
    private String api_key;

    @Value("${coolsms.api_secret}")
    private String api_secret;

    @Value("${coolsms.phone_number}")
    private String phoneNumber;

    @PostMapping("/send")
    public void send() {
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();

        params.put("to", phoneNumber);
        params.put("from", phoneNumber);
        params.put("type", "sms");
        params.put("text", "횐경설정 테스트입니다.");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
        } catch (CoolsmsException e) {
            log.error(e.getMessage());
        }
    }
}
