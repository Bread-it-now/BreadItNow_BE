package com.breaditnow.customer.alert.presentation.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductAlertResponse {
    private Long productId;
    private String productName;
    private String productImage;
    private List<String> releaseTimes;
    private Long bakeryId;
    private String bakeryName;
    private Boolean alertActive;
}
