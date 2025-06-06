package com.breaditnow.customer.product.presentation.response;

import com.breaditnow.customer.common.domain.DailyTime;
import com.breaditnow.customer.common.presentation.ReleaseTimeList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFavoriteDetailsResponse {
    private Long productId;
    private Long bakeryId;
    private String productName;
    private String productImage;
    private Integer price;
    @ReleaseTimeList
    private List<DailyTime> dailyTimes;
    private boolean isBakeryActive;
    private boolean isProductActive;
    private Double distance;
}
