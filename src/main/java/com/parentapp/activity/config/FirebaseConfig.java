package com.parentapp.activity.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {

            String firebaseJson = System.getenv("FIREBASE_CONFIG_JSON");

            if (firebaseJson == null || firebaseJson.isEmpty()) {
                throw new RuntimeException("FIREBASE_CONFIG_JSON env variable not set");
            }

            GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ByteArrayInputStream(firebaseJson.getBytes(StandardCharsets.UTF_8)));

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

        } catch (Exception e) {
            throw new RuntimeException("Firebase initialization failed", e);
        }
    }
}
