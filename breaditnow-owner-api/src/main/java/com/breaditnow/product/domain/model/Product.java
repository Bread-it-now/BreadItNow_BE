package com.breaditnow.product.domain.model;

import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.common.domain.ProductStatus;
import com.breaditnow.common.exception.OwnerException;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.breaditnow.common.exception.OwnerErrorCode.INVALID_STOCK;
import static com.breaditnow.common.exception.OwnerErrorCode.PRODUCT_NOT_FOUND;

@Getter
public class Product {
    private Long id;
    private Long bakeryId;
    private ProductInfo productInfo;
    private Integer displayOrder;
    private SalesPolicy salesPolicy;
    private Classification classification;
    private List<DailyTime> releaseTimes;
    private boolean deleted;

    @Builder
    public Product(Long id, Long bakeryId, ProductInfo productInfo, Integer displayOrder, SalesPolicy salesPolicy, Classification classification, List<DailyTime> releaseTimes, boolean deleted) {
        this.id = id;
        this.bakeryId = bakeryId;
        this.productInfo = productInfo;
        this.displayOrder = displayOrder;
        this.salesPolicy = salesPolicy;
        this.classification = classification;
        this.releaseTimes = releaseTimes;
        this.deleted = deleted;
    }

    public static Product create(Long bakeryId, ProductInfo productInfo, Integer displayOrder, SalesPolicy salesPolicy, Classification classification, List<DailyTime> releaseTimes) {
        return Product.builder()
                .bakeryId(bakeryId)
                .productInfo(productInfo)
                .displayOrder(displayOrder)
                .salesPolicy(salesPolicy)
                .classification(classification)
                .releaseTimes(releaseTimes)
                .deleted(false)
                .build();
    }

    public void update(ProductInfo newProductInfo, SalesPolicy newSalesPolicy, Classification newClassification, List<DailyTime> newReleaseTimes) {
        validateIsActive();
        this.productInfo = newProductInfo;
        this.salesPolicy = newSalesPolicy;
        this.classification = newClassification;
        this.releaseTimes = newReleaseTimes;
    }

    public void updateStock(Integer stock) {
        validateIsActive();
        if(stock < 0) {
            throw new OwnerException(INVALID_STOCK);
        }
        this.salesPolicy = this.salesPolicy.withStock(stock);
    }

    public void changeStatus(ProductStatus newStatus) {
        validateIsActive();
        this.salesPolicy = this.salesPolicy.withStatus(newStatus);
    }

    public void updateDisplayOrder(Integer newDisplayOrder) {
        validateIsActive();
        this.displayOrder = newDisplayOrder;
    }

    public void delete() {
        validateIsActive();
        this.deleted = true;
    }

    public void validateBelongsTo(Long bakeryId) {
        if(!getBakeryId().equals(bakeryId)){
            throw new OwnerException(PRODUCT_NOT_FOUND);
        }
    }

    public void validateIsActive() {
        if (this.deleted) {
            throw new OwnerException(PRODUCT_NOT_FOUND);
        }
    }

    public List<String> getReleaseTimesAsString() {
        if (this.releaseTimes == null || this.releaseTimes.isEmpty()) {
            return Collections.emptyList();
        }
        return this.releaseTimes.stream()
                .map(DailyTime::toString)
                .collect(Collectors.toList());
    }
}
