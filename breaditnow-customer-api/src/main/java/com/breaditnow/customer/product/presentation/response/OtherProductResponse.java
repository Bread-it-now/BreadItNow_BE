package com.breaditnow.customer.product.presentation.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OtherProductResponse {
    private Long productId;
    private String productName;
    private String productImage;
    private String productDescription;
    private Integer price;
    private Integer stock;
}
