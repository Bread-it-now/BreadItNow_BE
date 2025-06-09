package com.breaditnow.customer.alert.presentation.response;

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
    private List<String> releaseTimes;
}
