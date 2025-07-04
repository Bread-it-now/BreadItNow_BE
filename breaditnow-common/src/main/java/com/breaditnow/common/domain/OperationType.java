package com.breaditnow.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperationType {
    DECREASE("decrease"),
    INCREASE("increase");

    private final String value;
}
