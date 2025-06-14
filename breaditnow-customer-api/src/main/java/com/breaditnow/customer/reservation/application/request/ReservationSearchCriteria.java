package com.breaditnow.customer.reservation.application.request;

import com.breaditnow.customer.common.domain.vo.Pagination;
import com.breaditnow.customer.reservation.domain.ReservationStatus;
import com.breaditnow.customer.reservation.presentation.request.ReservationSearchRequest;
import io.micrometer.common.util.StringUtils;

public record ReservationSearchCriteria(Pagination pagination, ReservationStatus status) {
    public static ReservationSearchCriteria of(ReservationSearchRequest request) {
        Pagination pagination = Pagination.of(request.page(), request.size());
        if(StringUtils.isEmpty(request.status())) {
            return new ReservationSearchCriteria(pagination, null);
        }

        ReservationStatus status = ReservationStatus.valueOf(request.status().toUpperCase());
        return new ReservationSearchCriteria(pagination, status);
    }
}
