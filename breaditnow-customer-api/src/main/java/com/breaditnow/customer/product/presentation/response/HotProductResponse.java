package com.breaditnow.customer.product.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HotProductResponse {
    private Long productId;
    private Long bakeryId;
    private String bakeryName;
    private String productName;
    private String productImage;
    private Integer favoriteCount;
    private Integer reservationCount;
    private Integer price;
    private Integer stock;
    private Boolean isFavorite;
    private Double distance;
}
