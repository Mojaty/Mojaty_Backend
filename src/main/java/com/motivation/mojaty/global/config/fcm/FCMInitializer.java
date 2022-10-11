package com.motivation.mojaty.global.config.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

import static com.motivation.mojaty.global.config.fcm.FcmScopeUtil.*;

@Service
@Slf4j
public class FCMInitializer {

    private static final String[] SCOPES = { FIREBASE_SCOPE, CLOUD_PLATFORM_SCOPE, FIREBASE_READONLY_SCOPE };
    private static final String FIREBASE_CONFIG_PATH = "mojaty-e4362-firebase-adminsdk-juq3h-9d6c888b5c.json";

    @PostConstruct
    public void initialize() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
                    .createScoped(List.of(SCOPES));

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .build();
            FirebaseApp.initializeApp(options);
            
        } catch (IOException e) {
            log.info(">>>>>>>>FCM error");
            log.error(">>>>>>FCM error message : " + e.getMessage());
        }
    }
}
