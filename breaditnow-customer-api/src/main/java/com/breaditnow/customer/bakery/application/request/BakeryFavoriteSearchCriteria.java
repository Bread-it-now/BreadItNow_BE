package com.breaditnow.customer.bakery.application.request;

import com.breaditnow.customer.bakery.presentation.request.BakeryFavoriteSearchRequest;
import com.breaditnow.customer.common.domain.vo.GeoPoint;
import com.breaditnow.customer.common.domain.vo.Pagination;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.domain.domain.bakery.enumerate.SortType;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.COORDINATES_REQUIRED;
import static com.breaditnow.domain.domain.bakery.enumerate.SortType.DISTANCE;

public record BakeryFavoriteSearchCriteria(Pagination pagination, SortType sortType, GeoPoint location) {
    public static BakeryFavoriteSearchCriteria of(BakeryFavoriteSearchRequest request) {
        Pagination pagination = Pagination.of(request.page(), request.size());
        SortType sortType = SortType.of(request.sort());
        GeoPoint geoPoint = GeoPoint.of(request.latitude(), request.longitude());

        if (sortType == DISTANCE && geoPoint == null) {
            throw new CustomerException(COORDINATES_REQUIRED);
        }
        return new BakeryFavoriteSearchCriteria(pagination, sortType, geoPoint);
    }
}
