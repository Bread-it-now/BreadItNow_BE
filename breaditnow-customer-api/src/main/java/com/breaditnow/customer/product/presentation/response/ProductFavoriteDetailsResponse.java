package com.breaditnow.customer.product.presentation.response;

import com.breaditnow.customer.common.domain.ReleaseTime;
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
    private String name;
    private String image;
    private Integer price;
    @ReleaseTimeList
    private List<ReleaseTime> releaseTimes;
    private boolean isBakeryActive;
    private boolean isProductActive;
}
