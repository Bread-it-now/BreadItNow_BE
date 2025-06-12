package com.breaditnow.customer.bakery.presentation.response;

import com.breaditnow.customer.common.domain.vo.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public record BakeryFavoritePageResponse(List<BakeryFavoriteResponse> favorites, PageInfo pageInfo) {
    public static BakeryFavoritePageResponse of(Page<BakeryFavoriteResponse> bakeryFavoriteResponses) {
        return new BakeryFavoritePageResponse(bakeryFavoriteResponses.getContent(), PageInfo.of(bakeryFavoriteResponses));
    }
}
