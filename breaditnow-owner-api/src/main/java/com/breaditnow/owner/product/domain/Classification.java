package com.breaditnow.owner.product.domain;

import com.breaditnow.owner.global.exception.OwnerException;
import jakarta.persistence.Embeddable;

import java.util.List;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.BREAD_CATEGORY_TYPE_REQUIRED;

@Embeddable
public record Classification(
        ProductType type,
        List<Long> breadCategoryIds
) {
    public static Classification create(ProductType productType, List<Long> breadCategoryIds) {
        if (productType == null) {
            throw new OwnerException(BREAD_CATEGORY_TYPE_REQUIRED);
        }
        List<Long> validatedCategories = productType.getValidatedCategories(breadCategoryIds);
        return new Classification(productType, validatedCategories);
    }
}
