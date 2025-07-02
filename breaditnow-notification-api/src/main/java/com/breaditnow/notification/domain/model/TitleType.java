package com.breaditnow.notification.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TitleType {
    RESERVATION("예약"),
    STOCK("빵 알림");

    private final String title;
}
