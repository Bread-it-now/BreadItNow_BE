package com.breaditnow.bakery.application.request;

import com.breaditnow.bakery.presentation.request.HotBakerySearchRequest;
import com.breaditnow.common.domain.GeoPoint;
import com.breaditnow.common.domain.Pagination;
import com.breaditnow.common.domain.Period;
import com.breaditnow.common.domain.PeriodRange;
import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.product.domain.vo.HotSortType;

import static com.breaditnow.common.exception.CustomerErrorCode.PERIOD_REQUIRED_FOR_RESERVATION_SORT;
import static com.breaditnow.product.domain.vo.HotSortType.RESERVATION;

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
