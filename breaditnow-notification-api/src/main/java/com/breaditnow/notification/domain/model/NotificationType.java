package com.breaditnow.notification.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.breaditnow.common.domain.DailyTime.DATE_FORMATTER;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    RESERVATION_REQUESTED("예약 요청"){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            String content = String.format("(%s) %s 예약이 [요청] 되었습니다.\n[예약시간: %s]", data.customerNickname(), formatProductNames(data.productNames()), data.reservationTime().format(DATE_FORMATTER));
            return buildMessage(content);
        }
    },

    RESERVATION_APPROVED("예약 완료"){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            String content = String.format("(%s) %s 예약이 [완료] 되었습니다.\n[예약 번호: %d 픽업시간: %s]", data.customerNickname(),  formatProductNames(data.productNames()), data.reservationNumber(), data.pickupDeadline().format(DATE_FORMATTER));
            return buildMessage(content);
        }
    },

    RESERVATION_PARTIALLY_APPROVED("예약 부분 완료"){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            String content = String.format("(%s) %s 예약이 [부분 완료] 되었습니다.\n[앱에서 변경된 내역을 확인해주세요.]", data.bakeryName(), formatProductNames(data.productNames()));
            return buildMessage(content);
        }
    },

    RESERVATION_CANCELED_BY_CUSTOMER("예약 취소"){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            String content = String.format("(%s) %s 예약이 [취소] 되었습니다.\n[사유: %s]", data.bakeryName(), formatProductNames(data.productNames()), data.cancelReason());
            return buildMessage(content);
        }
    },

    RESERVATION_CANCELED_BY_OWNER("예약 취소"){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            String content = String.format("(%s) %s 예약이 [취소] 되었습니다.\n[사유: %s]", data.customerNickname(), formatProductNames(data.productNames()), data.cancelReason());
            return buildMessage(content);
        }
    },

    STOCK_ARRIVAL("재고 도착"){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            return null;
        }
    };

    private final String title;

    public abstract NotificationMessage createMessage(NotificationData data);

    protected NotificationMessage buildMessage(String content) {
        return new NotificationMessage(this.title, content);
    }

    private static String formatProductNames(List<String> productNames) {
        if (productNames == null || productNames.isEmpty()) {
            return "";
        }
        return String.join(", ", productNames);
    }
}
