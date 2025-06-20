package com.breaditnow.owner.product.infrastructure.persistence.jpa;

import com.breaditnow.domain.global.entity.BaseEntity;
import com.breaditnow.owner.bakery.domain.Image;
import com.breaditnow.owner.common.domain.DailyTime;
import com.breaditnow.owner.common.domain.Money;
import com.breaditnow.owner.common.jpa.DailyTimeListConverter;
import com.breaditnow.owner.product.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "bakery_id")
    private Long bakeryId;

    private String name;
    private String description;
    private String profileImageUrl;
    private Integer price;
    private Integer stock;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private Integer displayOrder;

    @Convert(converter = DailyTimeListConverter.class)
    @Column(name = "release_times")
    private List<DailyTime> releaseTimes;

    private boolean deleted;

    public static ProductEntity from(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .bakeryId(product.getBakeryId())
                .name(product.getProductInfo().name())
                .description(product.getProductInfo().description())
                .profileImageUrl(product.getProductInfo().getProfileImageUrl())
                .price(product.getSalesPolicy().price().amount())
                .stock(product.getSalesPolicy().stock())
                .status(product.getSalesPolicy().status())
                .productType(product.getClassification().type())
                .displayOrder(product.getDisplayOrder())
                .releaseTimes(product.getReleaseTimes())
                .deleted(product.isDeleted())
                .build();
    }

    public Product toDomain() {
        ProductInfo productInfo = ProductInfo.create(this.name, this.description, Image.create(this.profileImageUrl));
        SalesPolicy salesPolicy = new SalesPolicy(new Money(this.price), this.stock, this.status);
        Classification classification = new Classification(this.getProductType());

        return Product.builder()
                .id(this.getId())
                .bakeryId(this.getBakeryId())
                .productInfo(productInfo)
                .salesPolicy(salesPolicy)
                .classification(classification)
                .displayOrder(this.getDisplayOrder())
                .releaseTimes(this.getReleaseTimes())
                .deleted(this.isDeleted())
                .build();
    }
}
