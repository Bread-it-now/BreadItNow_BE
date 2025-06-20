package com.breaditnow.owner.product.infrastructure.presentation.response;

import com.breaditnow.owner.product.domain.Product;
import com.breaditnow.owner.product.domain.ProductStatus;
import com.breaditnow.owner.product.domain.ProductType;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductDetailResponse(
        Long productId,
        String name,
        String description,
        String imageUrl,
        Integer price,
        Integer stock,
        ProductStatus status,
        ProductType productType,
        List<String> releaseTimes,
        Integer displayOrder
) {
    public static ProductDetailResponse from(Product product) {
        return ProductDetailResponse.builder()
                .productId(product.getId())
                .name(product.getProductInfo().name())
                .description(product.getProductInfo().description())
                .imageUrl(product.getProductInfo().getProfileImageUrl())
                .price(product.getSalesPolicy().price().amount())
                .stock(product.getSalesPolicy().stock())
                .status(product.getSalesPolicy().status())
                .productType(product.getClassification().type())
                .releaseTimes(product.getReleaseTimes().stream().map(Object::toString).toList())
                .displayOrder(product.getDisplayOrder())
                .build();
    }
}
