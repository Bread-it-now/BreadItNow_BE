package com.breaditnow.customer.product.presentation.response;

import com.breaditnow.customer.common.domain.vo.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public record ProductFavoritePageDetailsResponse(List<ProductFavoriteDetailsResponse> favorites, PageInfo pageInfo) {
    public static ProductFavoritePageDetailsResponse of(Page<ProductFavoriteDetailsResponse> productFavoriteDetailsResponses) {
        return new ProductFavoritePageDetailsResponse(
                productFavoriteDetailsResponses.getContent(),
                PageInfo.of(productFavoriteDetailsResponses)
        );
    }
}
