package com.breaditnow.customer.product.application.request;

import com.breaditnow.customer.common.domain.vo.GeoPoint;
import com.breaditnow.customer.common.domain.vo.Pagination;
import com.breaditnow.customer.product.presentation.request.ProductFavoriteSearchRequest;
import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductFavoriteSearchCriteria {
    private final Pagination pagination;
    private final SortType sortType;
    private final GeoPoint location;

    public static ProductFavoriteSearchCriteria of(ProductFavoriteSearchRequest request) {
        Pagination pagination = Pagination.of(request.page(), request.size());
        SortType sortType = SortType.of(request.sort());
        GeoPoint geoPoint = GeoPoint.of(request.latitude(), request.longitude());
        return new ProductFavoriteSearchCriteria(pagination, sortType, geoPoint);
    }
}
