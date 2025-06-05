package com.breaditnow.customer.product.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFavoriteDetailsResponse {
    private Long productId;
    private Long bakeryId;
    private String name;
    private String image;
    private Integer price;
    private String releaseTimes;
    private boolean isBakeryActive;
    private boolean isProductActive;
}
