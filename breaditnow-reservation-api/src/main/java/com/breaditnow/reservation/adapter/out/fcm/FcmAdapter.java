package com.breaditnow.reservation.adapter.out.fcm;

import com.breaditnow.reservation.domain.port.out.FcmPort;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmAdapter implements FcmPort {
    private final FirebaseMessaging firebaseMessaging;

    @Async
    @Override
    public void sendNotification(String fcmToken, String title, String body) {
        if (StringUtils.isBlank(fcmToken)) {
            log.warn("FCM token is blank. Notification will not be sent.");
            return;
        }

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .build();

        try {
            String messageId = firebaseMessaging.send(message);
            log.info("Successfully sent FCM message: {} to token: {}", messageId, fcmToken);
        } catch (FirebaseMessagingException e) {
            handleFcmException(e, fcmToken);
        } catch (Exception e) {
            log.error("An unexpected error occurred while sending FCM message to token: {}", fcmToken, e);
        }
    }

    private void handleFcmException(FirebaseMessagingException e, String fcmToken) {
        switch (e.getMessagingErrorCode()) {
            case UNREGISTERED, INVALID_ARGUMENT -> log.warn("FCM token is invalid or unregistered. Token: {}. Error: {}", fcmToken, e.getMessage());
            case UNAVAILABLE -> log.error("FCM service is temporarily unavailable. Failed to send message to token: {}", fcmToken, e);
            default -> log.error("Failed to send FCM message to token: {}. ErrorCode: {}", fcmToken, e.getMessagingErrorCode(), e);
        }
    }
}
