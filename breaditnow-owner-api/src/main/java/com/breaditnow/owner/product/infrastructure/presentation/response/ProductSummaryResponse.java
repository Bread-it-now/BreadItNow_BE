package com.breaditnow.owner.product.infrastructure.presentation.response;

import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.domain.ProductStatus;
import lombok.Builder;

@Builder
public record ProductSummaryResponse(
        Long productId,
        String name,
        String imageUrl,
        Integer price,
        ProductStatus status
) {
    public static ProductSummaryResponse from(Product product) {
        return ProductSummaryResponse.builder()
                .productId(product.getId())
                .name(product.getProductInfo().name())
                .imageUrl(product.getProductInfo().getProfileImageUrl())
                .price(product.getSalesPolicy().price().amount())
                .status(product.getSalesPolicy().status())
                .build();
    }
}
