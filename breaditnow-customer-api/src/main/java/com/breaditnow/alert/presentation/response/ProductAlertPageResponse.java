package com.breaditnow.alert.presentation.response;

import com.breaditnow.common.domain.PageInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public record ProductAlertPageResponse(List<ProductAlertResponse> alerts, PageInfo pageInfo) {
    public static ProductAlertPageResponse of(Page<ProductAlertResponse> productAlertResponses) {
        return new ProductAlertPageResponse(productAlertResponses.getContent(), PageInfo.of(productAlertResponses));
    }
}
