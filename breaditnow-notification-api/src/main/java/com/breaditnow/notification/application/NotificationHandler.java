package com.breaditnow.notification.application;

import com.breaditnow.notification.adapter.in.dto.ReservationCreatedEvent;
import com.breaditnow.notification.domain.model.Notification;
import com.breaditnow.notification.domain.port.out.FcmPort;
import com.breaditnow.notification.domain.port.out.NotificationRepository;
import com.breaditnow.notification.domain.port.out.OwnerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.breaditnow.notification.domain.model.NotificationType.RESERVATION_CREATED;
import static com.breaditnow.notification.domain.model.TitleType.RESERVATION;
import static com.breaditnow.notification.domain.model.UserType.OWNER;

@Service
@RequiredArgsConstructor
public class NotificationHandler {
    private final FcmPort fcmPort;
    private final OwnerApiPort ownerApiPort;
    private final NotificationRepository notificationRepository;

    @Transactional
    public void handleReservationCreation(ReservationCreatedEvent event) {
        String fcmToken = ownerApiPort.findFcmTokenById(event.ownerId())
                .orElse(null);

        String content = formatOwnerContent(event);
        Notification notification = Notification.create(event.ownerId(), event.bakeryId(), OWNER, RESERVATION, RESERVATION_CREATED, content);
        notificationRepository.save(notification);

        if (fcmToken != null) {
            fcmPort.sendNotification(fcmToken, "예약", content);
        }
    }

    private String formatOwnerContent(ReservationCreatedEvent event) {
        String productSummary = String.join(", ", event.productNames());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd(E) a hh:mm", Locale.KOREAN);
        String formattedReservationTime = event.reservationTime().format(formatter);

        return String.format("(%s) %s 예약이 요청되었습니다. [%s]",
                event.customerName(),
                productSummary,
                formattedReservationTime
        );
    }
}
