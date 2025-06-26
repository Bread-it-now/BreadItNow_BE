package com.breaditnow.product.adapter.in.dto.response;

import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.model.ProductStatus;
import com.breaditnow.product.domain.model.ProductType;

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
