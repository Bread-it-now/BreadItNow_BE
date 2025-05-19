package com.breaditnow.customer.alert.controller.res;

import com.breaditnow.common.page.PageInfo;
import com.breaditnow.domain.domain.alert.entity.CustomerProductAlert;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record CustomerProductAlertPageResponse(
        List<CustomerProductAlertResponse> alerts,
        PageInfo pageInfo
) {
    public static CustomerProductAlertPageResponse of(List<CustomerProductAlertResponse> alerts, Page<CustomerProductAlert> alertsPage) {
        PageInfo pageInfo = PageInfo.builder()
                .totalElements(alertsPage.getTotalElements())
                .totalPages(alertsPage.getTotalPages())
                .isLast(alertsPage.isLast())
                .currPage(alertsPage.getPageable().getPageNumber())
                .build();

        return CustomerProductAlertPageResponse.builder()
                .alerts(alerts)
                .pageInfo(pageInfo)
                .build();
    }
}

