package com.breaditnow.customer.common.domain.vo;

import com.breaditnow.customer.common.exception.CustomerException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_PAGE_NUMBER;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_PAGE_SIZE;

public record Pagination(int page, int size) {
    private static final int MIN_PAGE = 0;
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 100;
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    public Pagination {
        validatePage(page);
        validateSize(size);
    }

    public static Pagination of(Integer page, Integer size) {
        return new Pagination(
                page != null ? page : DEFAULT_PAGE,
                size != null ? size : DEFAULT_SIZE
        );
    }

    public static Pagination from(Pageable pageable) {
        return new Pagination(pageable.getPageNumber(), pageable.getPageSize());
    }

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }

    public Pageable toPageable(Sort sort) {
        return PageRequest.of(page, size, sort);
    }

    private static void validatePage(int page) {
        if (page < MIN_PAGE) {
            throw new CustomerException(INVALID_PAGE_NUMBER);
        }
    }

    private static void validateSize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new CustomerException(INVALID_PAGE_SIZE);
        }
    }
}


