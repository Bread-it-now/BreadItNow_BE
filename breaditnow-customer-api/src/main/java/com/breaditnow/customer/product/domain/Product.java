package com.breaditnow.customer.product.domain;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.ONLY_BREAD_CAN_BE_FAVORITED;
//import static com.breaditnow.customer.common.exception.CustomerErrorCode.PRODUCT_NOT_BELONG_TO_BAKERY;

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
        if(!isBread()){
            throw new CustomerException(ONLY_BREAD_CAN_BE_FAVORITED);
        }
        this.favoriteCount++;
    }

    public void unfavorite() {
        this.favoriteCount--;
    }

    public boolean isBread() {
        return this.type == ProductType.BREAD;
    }

//    public void validateBelongsToBakery(Long bakeryId) {
//        if (!getBakeryId().equals(bakeryId)) {
//            throw new CustomerException(PRODUCT_NOT_BELONG_TO_BAKERY);
//        }
//    }
}