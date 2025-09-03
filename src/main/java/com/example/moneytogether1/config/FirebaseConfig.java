package com.example.moneytogether1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import jakarta.annotation.PostConstruct;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {
        try (InputStream serviceAccount = new ClassPathResource("firebase/firebase-service-key.json").getInputStream()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialized");
            }
        } catch (IOException e) {
            log.error("Firebase initialization failed", e);
        }
    }
}



