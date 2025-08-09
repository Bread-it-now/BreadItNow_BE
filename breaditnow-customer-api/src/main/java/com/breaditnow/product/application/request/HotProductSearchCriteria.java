package com.breaditnow.product.application.request;

import com.breaditnow.common.domain.GeoPoint;
import com.breaditnow.common.domain.Pagination;
import com.breaditnow.common.domain.Period;
import com.breaditnow.common.domain.PeriodRange;
import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.product.domain.vo.HotSortType;
import com.breaditnow.product.presentation.request.HotProductSearchRequest;

import static com.breaditnow.common.exception.CustomerErrorCode.PERIOD_REQUIRED_FOR_RESERVATION_SORT;
import static com.breaditnow.product.domain.vo.HotSortType.RESERVATION;

public record HotProductSearchCriteria(
        Pagination pagination,
        HotSortType hotSortType,
        PeriodRange periodRange,
        GeoPoint location
) {
    public static HotProductSearchCriteria of(HotProductSearchRequest request) {
        Pagination pagination = Pagination.of(request.page(), request.size());
        Period period = Period.of(request.period());
        PeriodRange periodRange = PeriodRange.of(period);
        HotSortType hotSortType = HotSortType.of(request.sort());
        GeoPoint location = GeoPoint.of(request.latitude(), request.longitude());

        if (hotSortType == RESERVATION && periodRange == null) {
            throw new CustomerException(PERIOD_REQUIRED_FOR_RESERVATION_SORT);
        }

        return new HotProductSearchCriteria(pagination, hotSortType, periodRange, location);
    }
}
