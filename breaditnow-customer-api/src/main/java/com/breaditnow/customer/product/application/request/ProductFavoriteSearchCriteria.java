package com.breaditnow.customer.product.application.request;

import com.breaditnow.customer.common.domain.vo.GeoPoint;
import com.breaditnow.customer.common.domain.vo.Pagination;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.product.presentation.request.ProductFavoriteSearchRequest;
import com.breaditnow.domain.domain.bakery.enumerate.SortType;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.COORDINATES_REQUIRED;

public record ProductFavoriteSearchCriteria(Pagination pagination, SortType sortType, GeoPoint location) {
    public static ProductFavoriteSearchCriteria of(ProductFavoriteSearchRequest request) {
        Pagination pagination = Pagination.of(request.page(), request.size());
        SortType sortType = SortType.of(request.sort());
        GeoPoint geoPoint = GeoPoint.of(request.latitude(), request.longitude());

        if (sortType == SortType.DISTANCE && geoPoint == null) {
            throw new CustomerException(COORDINATES_REQUIRED);
        }

        return new ProductFavoriteSearchCriteria(pagination, sortType, geoPoint);
    }
}
