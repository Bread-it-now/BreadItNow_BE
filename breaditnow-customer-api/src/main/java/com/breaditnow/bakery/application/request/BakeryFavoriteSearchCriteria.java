package com.breaditnow.bakery.application.request;

import com.breaditnow.common.domain.GeoPoint;
import com.breaditnow.common.domain.Pagination;
import com.breaditnow.common.domain.SortType;
import com.breaditnow.bakery.presentation.request.BakeryFavoriteSearchRequest;
import com.breaditnow.common.exception.CustomerException;

import static com.breaditnow.common.domain.SortType.DISTANCE;
import static com.breaditnow.common.exception.CustomerErrorCode.COORDINATES_REQUIRED;

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
