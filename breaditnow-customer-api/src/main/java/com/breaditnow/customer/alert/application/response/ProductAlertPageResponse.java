package com.breaditnow.customer.alert.application.response;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.alert.entity.CustomerProductAlert;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record ProductAlertPageResponse(
        List<ProductAlertResponse> alerts,
        PageInfo pageInfo
) {
    public static ProductAlertPageResponse of(List<ProductAlertResponse> alerts, Page<CustomerProductAlert> alertsPage) {
        PageInfo pageInfo = PageInfo.builder()
                .totalElements(alertsPage.getTotalElements())
                .totalPages(alertsPage.getTotalPages())
                .isLast(alertsPage.isLast())
                .currPage(alertsPage.getPageable().getPageNumber())
                .build();

        return ProductAlertPageResponse.builder()
                .alerts(alerts)
                .pageInfo(pageInfo)
                .build();
    }
}

