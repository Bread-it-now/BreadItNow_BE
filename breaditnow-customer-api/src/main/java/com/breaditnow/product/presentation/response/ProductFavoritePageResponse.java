package com.breaditnow.product.presentation.response;

import com.breaditnow.common.domain.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public record ProductFavoritePageResponse(List<ProductFavoriteResponse> favorites, PageInfo pageInfo) {
    public static ProductFavoritePageResponse of(Page<ProductFavoriteResponse> productFavoriteResponses) {
        return new ProductFavoritePageResponse(productFavoriteResponses.getContent(), PageInfo.of(productFavoriteResponses));
    }
}
