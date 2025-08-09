package com.breaditnow.product.application.request;

import com.breaditnow.common.domain.GeoPoint;
import com.breaditnow.common.domain.Pagination;
import com.breaditnow.common.domain.SortType;
import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.product.presentation.request.ProductFavoriteSearchRequest;

import static com.breaditnow.common.domain.SortType.DISTANCE;
import static com.breaditnow.common.exception.CustomerErrorCode.COORDINATES_REQUIRED;

public record ProductFavoriteSearchCriteria(Pagination pagination, SortType sortType, GeoPoint location) {
    public static ProductFavoriteSearchCriteria of(ProductFavoriteSearchRequest request) {
        Pagination pagination = Pagination.of(request.page(), request.size());
        SortType sortType = SortType.of(request.sort());
        GeoPoint geoPoint = GeoPoint.of(request.latitude(), request.longitude());

        if (sortType == DISTANCE && geoPoint == null) {
            throw new CustomerException(COORDINATES_REQUIRED);
        }

        return new ProductFavoriteSearchCriteria(pagination, sortType, geoPoint);
    }
}
