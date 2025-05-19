package com.breaditnow.customer.product.service;

import com.breaditnow.customer.common.req.GeoPointRequest;
import com.breaditnow.customer.product.controller.res.ProductFavoritePageResponse;
import com.breaditnow.domain.domain.bakery.enumerate.SortType;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;
import com.breaditnow.domain.global.dto.GeoPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFavoritePageService {
    private final CustomerProductFavoriteRepository customerProductFavoriteRepository;

    public ProductFavoritePageResponse getFavorites(Long customerId, int page, int size, String sort,
                                                    GeoPointRequest geoPointRequest) {
        GeoPoint currentGeoPoint = geoPointRequest.toEntity();
        Pageable pageable = PageRequest.of(page, size);
        SortType sortType = SortType.from(sort);

        return ProductFavoritePageResponse.of(
                customerProductFavoriteRepository.findProductFavorites(customerId, pageable, sortType, currentGeoPoint));
    }
}

