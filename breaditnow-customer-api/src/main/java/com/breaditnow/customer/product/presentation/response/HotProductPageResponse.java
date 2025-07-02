package com.breaditnow.customer.product.presentation.response;

import com.breaditnow.common.domain.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public record HotProductPageResponse(List<HotProductResponse> hotProducts, PageInfo pageInfo) {
    public static HotProductPageResponse of(Page<HotProductResponse> hotProductResponses) {
        return new HotProductPageResponse(hotProductResponses.getContent(), PageInfo.of(hotProductResponses));
    }
}
