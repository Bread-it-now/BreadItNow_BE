package com.breaditnow.common.domain;

import com.breaditnow.common.exception.BreaditnowException;
import com.breaditnow.common.exception.CommonErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum SortType {
    DISTANCE, // 거리순 정렬
    LATEST,   // 최신순 정렬
    POPULAR;   // 인기순 정렬

    @JsonCreator
    public static SortType of(String type) {
        try {
            return SortType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BreaditnowException(CommonErrorCode.SORT_CONDITION_NOT_FOUND);
        }
    }
}
