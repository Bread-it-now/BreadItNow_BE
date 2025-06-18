package com.breaditnow.owner.product.domain;

import com.breaditnow.owner.global.exception.OwnerException;

import java.util.List;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.BREAD_CATEGORY_MUST_BE_EMPTY;
import static com.breaditnow.owner.global.exception.OwnerErrorCode.BREAD_CATEGORY_REQUIRED;

public enum ProductType {
    BREAD {
        @Override
        public List<Long> getValidatedCategories(List<Long> breadCategoryIds) {
            if (breadCategoryIds == null || breadCategoryIds.isEmpty()) {
                throw new OwnerException(BREAD_CATEGORY_REQUIRED);
            }
            return breadCategoryIds;
        }
    },
    OTHER {
        @Override
        public List<Long> getValidatedCategories(List<Long> breadCategoryIds) {
            if (breadCategoryIds != null && !breadCategoryIds.isEmpty()) {
                throw new OwnerException(BREAD_CATEGORY_MUST_BE_EMPTY);
            }
            return List.of();
        }
    };

    public abstract List<Long> getValidatedCategories(List<Long> breadCategoryIds);

}
