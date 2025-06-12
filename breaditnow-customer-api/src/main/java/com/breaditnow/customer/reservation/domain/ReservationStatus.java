package com.breaditnow.customer.reservation.domain;

// hook으로 처리?
public enum ReservationStatus {
    WAITING, APPROVED, PARTIAL_APPROVED, CANCELLED
    // PRATIAL_APPROVED는 예약 부분 승인 상태이고, 부분 접수 사유를 알려주게 됩니다.
    // CANCELLED는 예약이 취소된 상태이고, 취소 사유에 대해서 알려주게 됩니다.
}