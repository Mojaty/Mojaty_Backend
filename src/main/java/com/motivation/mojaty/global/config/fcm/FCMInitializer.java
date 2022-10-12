package com.motivation.mojaty.global.config.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class FCMInitializer {

    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/cloud-platform";
    private static final String[] SCOPES = { MESSAGING_SCOPE };
    private static final String FIREBASE_CONFIG_PATH = "mojaty-e4362-firebase-adminsdk-juq3h-9d6c888b5c.json";

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())).build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.info(">>>>>>>>FCM error");
            log.error(">>>>>>FCM error message : " + e.getMessage());
        }
    }
}
