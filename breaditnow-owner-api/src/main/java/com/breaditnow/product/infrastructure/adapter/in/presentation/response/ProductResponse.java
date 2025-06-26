package com.breaditnow.product.infrastructure.adapter.in.presentation.response;

import com.breaditnow.product.domain.Product;
import com.breaditnow.product.domain.ProductStatus;
import com.breaditnow.product.domain.ProductType;

import java.util.List;

public record ProductResponse(
        Long productId,
        ProductStatus status,
        ProductType productType,
        String name,
        Integer price,
        String imageUrl,
        String description,
        List<String> releaseTimes,
        Integer stock,
        Integer displayOrder
) {
    public static ProductResponse of(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductResponse(
                product.getId(),
                product.getSalesPolicy().status(),
                product.getClassification().type(),
                product.getProductInfo().name(),
                product.getSalesPolicy().price().getAmount(),
                product.getProductInfo().getProfileImageUrl(),
                product.getProductInfo().description(),
                product.getReleaseTimesAsString(),
                product.getSalesPolicy().stock(),
                product.getDisplayOrder()
        );
    }
}
