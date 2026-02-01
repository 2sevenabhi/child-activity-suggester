package com.parentapp.activity.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.parentapp.activity.entity.ParentEntity;
import com.parentapp.activity.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ParentRepository parentRepository;

    public void sendToParent(Long parentId, String messageText) {

        ParentEntity parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        String token = parent.getDeviceToken();

        if (token == null || token.isBlank()) {
            log.warn("⚠ No device token for parent {}", parentId);
            return;
        }

        try {

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(
                            Notification.builder()
                                    .setTitle("Today's Activity Suggestion 👶📚")
                                    .setBody(messageText)
                                    .build())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);

            log.info("✅ Push sent successfully: {}", response);

        } catch (Exception e) {
            log.error("❌ Push failed for parent {}: {}", parentId, e.getMessage());
        }
    }
}
