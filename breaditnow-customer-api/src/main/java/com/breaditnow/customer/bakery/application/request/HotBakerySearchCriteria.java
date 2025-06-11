package com.breaditnow.customer.bakery.application.request;

import com.breaditnow.customer.bakery.presentation.request.HotBakerySearchRequest;
import com.breaditnow.customer.common.domain.vo.GeoPoint;
import com.breaditnow.customer.common.domain.vo.Pagination;
import com.breaditnow.customer.common.domain.vo.Period;
import com.breaditnow.customer.common.domain.vo.PeriodRange;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.product.domain.vo.HotSortType;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.PERIOD_REQUIRED_FOR_RESERVATION_SORT;
import static com.breaditnow.customer.product.domain.vo.HotSortType.RESERVATION;

public record HotBakerySearchCriteria(
        Pagination pagination,
        HotSortType hotSortType,
        PeriodRange periodRange,
        GeoPoint location
)  {
    public static HotBakerySearchCriteria of(HotBakerySearchRequest request) {
        Pagination pagination = Pagination.of(request.page(), request.size());
        Period period = Period.of(request.period());
        PeriodRange periodRange = PeriodRange.of(period);
        HotSortType hotSortType = HotSortType.of(request.sort());
        GeoPoint location = GeoPoint.of(request.latitude(), request.longitude());

        if (hotSortType == RESERVATION && periodRange == null) {
            throw new CustomerException(PERIOD_REQUIRED_FOR_RESERVATION_SORT);
        }

        return new HotBakerySearchCriteria(pagination, hotSortType, periodRange, location);
    }
}
