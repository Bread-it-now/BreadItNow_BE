package com.breaditnow.customer.alert.presentation.response;

import com.breaditnow.customer.common.domain.DailyTime;
import com.breaditnow.customer.common.presentation.ReleaseTimeList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TodayProductAlertResponse {
    private Long bakeryId;
    private String bakeryName;
    private Long productId;
    private String productName;
    @ReleaseTimeList
    private List<DailyTime> releaseTimes;
}
