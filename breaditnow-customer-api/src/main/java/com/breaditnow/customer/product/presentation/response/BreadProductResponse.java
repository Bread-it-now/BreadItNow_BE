package com.breaditnow.customer.product.presentation.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BreadProductResponse {
    private Long productId;
    private String productName;
    private String productImage;
    private String productDescription;
    private List<String> releaseTimes;
    private Boolean isProductFavorite;
    private Boolean isProductAlarm;
}
