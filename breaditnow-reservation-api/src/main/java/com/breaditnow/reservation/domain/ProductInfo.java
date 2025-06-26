package com.breaditnow.reservation.domain;

import com.breaditnow.common.domain.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Entity
@Table(name = "product_info")
public class ProductInfo {
    @Id
    private Long productId;

    @Column(nullable = false)
    private Long bakeryId;

    @Column(nullable = false)
    private String name;

    private String imageUrl;

    @Column(nullable = false)
    private Integer price;

    @Enumerated(STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(nullable = false)
    private boolean isDeleted = false;

    public boolean isReservable() {
        return !isDeleted && status == ProductStatus.FOR_SALE;
    }

    public void update(String name, String imageUrl, Integer price, ProductStatus status) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.status = status;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
