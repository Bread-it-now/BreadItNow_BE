package com.breaditnow.customer.alert.presentation.response;

import com.breaditnow.customer.common.domain.DailyTime;
import com.breaditnow.customer.common.presentation.ReleaseTimeList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductAlertResponse {
    private Long productId;
    private String productName;
    private String productImage;
    @ReleaseTimeList
    private List<DailyTime> releaseTimes;
    private Long bakeryId;
    private String bakeryName;
    private Boolean alertActive;
}
