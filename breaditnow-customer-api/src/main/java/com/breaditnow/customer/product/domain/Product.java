package com.breaditnow.customer.product.domain;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.common.domain.DailyTime;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class Product {
    private final Long id;
    private final Long bakeryId;
    private final String name;
    private final Integer stock;
    private final Money price;
    private final String imageUrl;
    private final String description;
    private final Integer displayOrder;
    private final boolean isActive;
    private final boolean isHidden;
    private Integer favoriteCount;
    private final List<DailyTime> releaseTimes;
    private final ProductType type;

    @Builder
    private Product(Long id, Long bakeryId, String name, Integer stock, Money price, String imageUrl, String description,
                    Integer displayOrder, boolean isActive, boolean isHidden, Integer favoriteCount, List<DailyTime> releaseTimes, ProductType type) {
        this.id = id;
        this.bakeryId = bakeryId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.displayOrder = displayOrder;
        this.isActive = isActive;
        this.isHidden = isHidden;
        this.favoriteCount = favoriteCount;
        this.releaseTimes = releaseTimes;
        this.type = type;
    }

    public void favorite() {
        this.favoriteCount++;
    }

    public void unfavorite() {
        this.favoriteCount--;
    }
}