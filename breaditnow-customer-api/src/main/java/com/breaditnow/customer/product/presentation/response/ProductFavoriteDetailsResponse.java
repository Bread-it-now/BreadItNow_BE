package com.breaditnow.customer.product.presentation.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductFavoriteDetailsResponse {
    private Long productId;
    private Long bakeryId;
    private String productName;
    private String productImage;
    private Integer price;
    private List<String> releaseTimes;
    private boolean isBakeryActive;
    private boolean isProductActive;
    private Double distance;
}
