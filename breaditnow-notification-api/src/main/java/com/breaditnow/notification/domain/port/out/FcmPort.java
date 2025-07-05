package com.breaditnow.notification.domain.port.out;

public interface FcmPort {
    void sendNotification(String fcmToken, String title, String body);
}
