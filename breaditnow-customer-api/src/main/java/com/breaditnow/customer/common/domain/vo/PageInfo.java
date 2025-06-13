package com.breaditnow.customer.common.domain.vo;

import org.springframework.data.domain.Page;

public record PageInfo(long totalElements, int totalPages, boolean isLast, int currPage) {
    public static <T> PageInfo of(Page<T> page) {
        return new PageInfo(
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getNumber()
        );
    }

    public static PageInfo empty() {
        return new PageInfo(0, 0, true, 0);
    }
}

