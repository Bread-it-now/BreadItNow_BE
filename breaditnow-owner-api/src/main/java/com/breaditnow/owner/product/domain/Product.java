package com.breaditnow.owner.product.domain;

import com.breaditnow.owner.common.domain.DailyTime;
import com.breaditnow.owner.global.exception.OwnerException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.*;

@Getter
public class Product {
    private Long id;
    private Long bakeryId;
    private ProductInfo productInfo;
    private Integer displayOrder;
    private SalesPolicy salesPolicy;
    private Classification classification;
    private List<DailyTime> releaseTimes;

    @Builder
    public Product(Long id, Long bakeryId, ProductInfo productInfo, Integer displayOrder, SalesPolicy salesPolicy, Classification classification, List<DailyTime> releaseTimes) {
        this.id = id;
        this.bakeryId = bakeryId;
        this.productInfo = productInfo;
        this.displayOrder = displayOrder;
        this.salesPolicy = salesPolicy;
        this.classification = classification;
        this.releaseTimes = releaseTimes;
    }

    public static Product create(Long bakeryId, ProductInfo productInfo, Integer displayOrder, SalesPolicy salesPolicy, Classification classification, List<DailyTime> releaseTimes) {
        return Product.builder()
                .bakeryId(bakeryId)
                .productInfo(productInfo)
                .displayOrder(displayOrder)
                .salesPolicy(salesPolicy)
                .classification(classification)
                .releaseTimes(releaseTimes)
                .build();
    }

    public void update(ProductInfo newProductInfo, SalesPolicy newSalesPolicy, Classification newClassification, List<DailyTime> newReleaseTimes) {
        this.productInfo = newProductInfo;
        this.salesPolicy = newSalesPolicy;
        this.classification = newClassification;
        this.releaseTimes = newReleaseTimes;
    }

    public void updateStock(Integer stock) {
        if(stock < 0) {
            throw new OwnerException(INVALID_STOCK);
        }
        this.salesPolicy = this.salesPolicy.withStock(stock);
    }

    public void changeStatus(ProductStatus newStatus) {
        this.salesPolicy = this.salesPolicy.withStatus(newStatus);
    }

    public void validateBelongsTo(Long bakeryId) {
        if(!getBakeryId().equals(bakeryId)){
            throw new OwnerException(PRODUCT_NOT_IN_BAKERY);
        }
    }
}
