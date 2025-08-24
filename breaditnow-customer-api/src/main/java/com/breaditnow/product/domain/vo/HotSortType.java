package com.breaditnow.product.domain.vo;

import com.breaditnow.common.exception.CustomerErrorCode;
import com.breaditnow.common.exception.CustomerException;

public enum HotSortType {
    RESERVATION, FAVORITE;

    public static HotSortType of(String hotSortType) {
        if (hotSortType == null) return null;

        try {
            return HotSortType.valueOf(hotSortType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomerException(CustomerErrorCode.INVALID_HOT_SORT_TYPE);
        }
    }
}
