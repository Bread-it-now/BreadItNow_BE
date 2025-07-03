package com.breaditnow.notification.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.breaditnow.common.domain.DailyTime.DATE_FORMATTER;
import static com.breaditnow.notification.domain.model.TitleType.RESERVATION;
import static com.breaditnow.notification.domain.model.TitleType.STOCK;

@Getter
@RequiredArgsConstructor
public enum NotificationType {

    RESERVATION_REQUESTED(RESERVATION){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            return new NotificationMessage(
                "예약 요청",
                    String.format("(%s) %s 예약이 [요청] 되었습니다.\n[예약시간: %s]",
                            data.customerNickname(),
                            formatProductNames(data.productNames()),
                            data.reservationTime().format(DATE_FORMATTER)
                    )
            );
        }
    },

    RESERVATION_APPROVED(RESERVATION){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            return new NotificationMessage(
                "예약 완료",
                    String.format("(%s) %s 예약이 [완료] 되었습니다.\n[%s 까지 픽업해주세요!]",
                            data.bakeryName(),
                            formatProductNames(data.productNames()),
                            data.pickupDeadline().format(DATE_FORMATTER)
                    )
            );
        }
    },

    RESERVATION_PARTIALLY_APPROVED(RESERVATION){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            return new NotificationMessage(
                "예약 부분 완료",
                    String.format("(%s) %s 예약이 [부분 완료] 되었습니다.\n[앱에서 변경된 내역을 확인해주세요.]",
                            data.bakeryName(),
                            formatProductNames(data.productNames())
                    )
            );
        }
    },

    RESERVATION_CANCELED_BY_CUSTOMER(RESERVATION){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            return new NotificationMessage(
                    "예약 취소",
                    String.format("(%s) %s 예약이 [취소] 되었습니다.\n[사유: %s]",
                            data.bakeryName(),
                            formatProductNames(data.productNames()),
                            data.cancelReason()
                    )
            );
        }
    },

    RESERVATION_CANCELED_BY_OWNER(RESERVATION){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            return new NotificationMessage(
                    "예약 취소",
                    String.format("(%s) %s 예약이 [취소] 되었습니다.",
                            data.customerNickname(),
                            formatProductNames(data.productNames())
                    )
            );
        }
    },

    STOCK_ARRIVAL(STOCK){
        @Override
        public NotificationMessage createMessage(NotificationData data) {
            return null;
        }
    };

    private final TitleType titleType;
    public abstract NotificationMessage createMessage(NotificationData data);

    private static String formatProductNames(List<String> productNames) {
        if (productNames == null || productNames.isEmpty()) {
            return "";
        }
        return String.join(", ", productNames);
    }
}
