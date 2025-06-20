package com.breaditnow.owner.product.infrastructure.presentation.response;

import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.domain.ProductStatus;
import com.breaditnow.owner.product.domain.ProductType;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductSummaryResponse(
        Long productId,
        ProductType productType,
        String productName,
        Integer price,
        Integer stock,
        String imageUrl,
        String description,
        List<String> releaseTimes,
        ProductStatus status
) {
    public static ProductSummaryResponse from(Product product) {
        return ProductSummaryResponse.builder()
                .productId(product.getId())
                .productType(product.getClassification().type())
                .productName(product.getProductInfo().name())
                .price(product.getSalesPolicy().price().amount())
                .stock(product.getSalesPolicy().stock())
                .imageUrl(product.getProductInfo().getProfileImageUrl())
                .releaseTimes(product.getReleaseTimes().stream().map(Object::toString).toList())
                .status(product.getSalesPolicy().status())
                .build();
    }
}
