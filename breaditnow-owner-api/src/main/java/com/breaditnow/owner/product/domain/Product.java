package com.breaditnow.owner.product.domain;

import com.breaditnow.owner.common.domain.DailyTime;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

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
}
