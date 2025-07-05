package com.breaditnow.notification.application.dto;

import com.breaditnow.common.domain.NotificationTypeDto;
import com.breaditnow.common.event.NotificationRequiredEvent;
import com.breaditnow.notification.domain.model.NotificationMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class NotificationTypeMessageUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.KOREAN);

    private NotificationTypeMessageUtil() {}

    public static NotificationMessage createMessage(NotificationRequiredEvent event) {
        String title = getTitle(event.notificationTypeDto());
        String content = switch (event.notificationTypeDto()) {
            case RESERVATION_REQUESTED -> getReservationRequestedContent(event.customerNickName(), event.productNames(), event.reservationTime());
            case RESERVATION_APPROVED -> getReservationApprovedContent(event.bakeryName(), event.productNames(), event.reservationNumber(), event.pickupDeadline());
            case RESERVATION_PARTIALLY_APPROVED -> getReservationPartiallyApprovedContent(event.bakeryName(), event.productNames());
            case RESERVATION_CANCELED_BY_CUSTOMER -> getReservationCanceledByCustomerContent(event.customerNickName(), event.productNames(), event.cancelReason());
            case RESERVATION_CANCELED_BY_OWNER -> getReservationCanceledByOwnerContent(event.bakeryName(), event.cancelReason());
            case RESERVATION_APPROVAL_FAILED -> getReservationApprovalFailedContent(event.customerNickName(), event.productNames(), event.cancelReason());
        };
        return new NotificationMessage(title, content);
    }

    private static String getTitle(NotificationTypeDto type) {
        return switch (type) {
            case RESERVATION_REQUESTED -> "예약 요청";
            case RESERVATION_APPROVED -> "예약 완료";
            case RESERVATION_PARTIALLY_APPROVED -> "예약 부분 완료";
            case RESERVATION_CANCELED_BY_CUSTOMER, RESERVATION_CANCELED_BY_OWNER -> "예약 취소";
            case RESERVATION_APPROVAL_FAILED -> "예약 승인 실패";
        };
    }

    private static String getReservationRequestedContent(String customerNickname, List<String> productNames, LocalDateTime reservationTime) {
        return String.format("(%s)님의 %s 예약이 [요청] 되었습니다.\n[예약시간: %s]", customerNickname, formatProductNames(productNames), formatDate(reservationTime));
    }

    private static String getReservationApprovedContent(String bakeryName, List<String> productNames, Long reservationNumber, LocalDateTime pickupDeadline) {
        return String.format("(%s)빵집의 %s 예약이 [완료] 되었습니다.\n[예약 번호: %d 픽업시간: %s]", bakeryName, formatProductNames(productNames), reservationNumber, formatDate(pickupDeadline));
    }

    private static String getReservationPartiallyApprovedContent(String bakeryName, List<String> productNames) {
        return String.format("(%s)님의 %s 예약이 [부분 완료] 되었습니다.\n[앱에서 변경된 내역을 확인해주세요.]", bakeryName, formatProductNames(productNames));
    }

    private static String getReservationCanceledByCustomerContent(String customerNickname, List<String> productNames, String cancelReason) {
        return String.format("(%s)님의 %s 예약이 [취소] 되었습니다.\n[사유: %s]", customerNickname, formatProductNames(productNames), cancelReason);
    }

    private static String getReservationCanceledByOwnerContent(String bakeryName, String cancelReason) {
        return String.format("(%s)빵집의 예약이 [취소] 되었습니다.\n[사유: %s]", bakeryName, cancelReason);
    }

    private static String getReservationApprovalFailedContent(String customerNickname, List<String> productNames, String reason) {
        return String.format("(%s)님의 %s 예약이 [승인 실패] 되었습니다.\n[사유: %s]", customerNickname, formatProductNames(productNames), reason);
    }

    private static String formatProductNames(List<String> productNames) {
        if (productNames == null || productNames.isEmpty()) return "";
        return String.join(", ", productNames);
    }

    private static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DATE_FORMATTER);
    }
}
