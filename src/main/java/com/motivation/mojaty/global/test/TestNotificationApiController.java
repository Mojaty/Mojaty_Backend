package com.motivation.mojaty.global.test;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/notification")
@Slf4j
public class TestNotificationApiController {

    @PostMapping("/send")
    public void send() {
        String api_key = "NCSN8R469KYWUCLO";
        String api_secret = "O7K8PAC5IH8GJWTIJJBFQDIFRRZIR5GR";
        Message coolsms = new Message(api_key, api_secret);
        HashMap<String, String> params = new HashMap<>();

        params.put("to", "01079290105");
//        params.put("from", "01079290105");
        params.put("from", "01029401509");
        params.put("type", "sms");
        params.put("text", "명언입니다.");

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
        } catch (CoolsmsException e) {
            log.error(e.getMessage());
        }
    }
}
