package com.breaditnow.domain.domain.product.entity;

import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.product.enumerate.ProductType;
import com.breaditnow.domain.global.entity.BaseEntity;
import com.breaditnow.domain.global.exception.DomainException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.breaditnow.domain.global.exception.DomainErrorCode.PRODUCT_ALREADY_HIDDEN;
import static com.breaditnow.domain.global.exception.DomainErrorCode.PRODUCT_ALREADY_UNHIDDEN;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(exclude = {"bakery", "breadCategories"})
@Table(name = "P_Product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bakery_id", nullable = false)
    private Bakery bakery;

    @Enumerated(STRING)
    private ProductType type;

    @Column(nullable = false)
    private String name;

    private Integer price;

    private String image;

    private String description;

    private String releaseTime;

    private Integer stock;

    private boolean isActive;

    private boolean isHidden;

    private Integer displayOrder;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductBreadCategory> breadCategories = new ArrayList<>();

    @Builder
    public Product(Bakery bakery, ProductType type, String name, Integer price, String image, String description,
                   Integer stock, String releaseTime, boolean isActive, boolean isHidden) {
        this.bakery = bakery;
        this.type = type;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.releaseTime = releaseTime;
        this.stock = stock;
        this.isActive = isActive;
        this.isHidden = isHidden;
    }

    public void update(Product product) {
        this.bakery = product.getBakery();
        this.type = product.getType();
        this.name = product.getName();
        this.price = product.getPrice();
        this.image = product.getImage();
        this.description = product.getDescription();
        this.releaseTime = product.getReleaseTime();
    }

    public void updateActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void updateDisplayOrder(int order) {
        this.displayOrder = order;
    }

    public void updateStock(int stock) {
        this.stock = stock;
    }

    public void hide() {
        if (this.isHidden) {
            throw new DomainException(PRODUCT_ALREADY_HIDDEN);
        }

        this.isHidden = true;
        updateDisplayOrder(-1);
    }

    public void unhide(int nextDisplayOrder) {
        if (!this.isHidden) {
            throw new DomainException(PRODUCT_ALREADY_UNHIDDEN);
        }

        this.isHidden = false;
        updateDisplayOrder(nextDisplayOrder);
    }
}
