package com.breaditnow.owner.product.infrastructure.persistence.jpa;

import com.breaditnow.domain.global.entity.BaseEntity;
import com.breaditnow.owner.common.domain.DailyTime;
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

    @Embedded
    private ProductInfo productInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "price.amount", column = @Column(name = "price"))
    })
    private SalesPolicy salesPolicy;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType type;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_bread_category", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "bread_category_id")
    private List<Long> breadCategoryIds;

    private Integer displayOrder;

    @Convert(converter = DailyTimeListConverter.class)
    @Column(name = "release_times")
    private List<DailyTime> releaseTimes;

    private boolean deleted;

    public static ProductEntity from(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .bakeryId(product.getBakeryId())
                .productInfo(product.getProductInfo())
                .salesPolicy(product.getSalesPolicy())
                .breadCategoryIds(product.getClassification().breadCategoryIds())
                .type(product.getClassification().type())
                .displayOrder(product.getDisplayOrder())
                .releaseTimes(product.getReleaseTimes())
                .deleted(product.isDeleted())
                .build();
    }

    public Product toDomain() {
        return Product.builder()
                .id(this.getId())
                .bakeryId(this.getBakeryId())
                .productInfo(this.getProductInfo())
                .salesPolicy(this.getSalesPolicy())
                .classification(new Classification(this.getType(), this.getBreadCategoryIds()))
                .displayOrder(this.getDisplayOrder())
                .releaseTimes(this.getReleaseTimes())
                .deleted(this.isDeleted())
                .build();
    }
}
