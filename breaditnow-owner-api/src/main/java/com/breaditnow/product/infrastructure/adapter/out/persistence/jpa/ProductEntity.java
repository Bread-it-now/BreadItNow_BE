package com.breaditnow.product.infrastructure.adapter.out.persistence.jpa;

import com.breaditnow.common.domain.Money;
import com.breaditnow.bakery.domain.model.Image;
import com.breaditnow.common.domain.BaseEntity;
import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.product.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product")
@Where(clause = "deleted = false")
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

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Convert(converter = DailyTimeListConverter.class)
    private List<String> releaseTimes;

    private String name;
    private String description;
    private String profileImageUrl;
    private Integer price;
    private Integer stock;
    private Integer displayOrder;
    private boolean deleted;

    public static ProductEntity from(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .bakeryId(product.getBakeryId())
                .name(product.getProductInfo().name())
                .description(product.getProductInfo().description())
                .profileImageUrl(product.getProductInfo().getProfileImageUrl())
                .price(product.getSalesPolicy().price().getAmount())
                .stock(product.getSalesPolicy().stock())
                .status(product.getSalesPolicy().status())
                .productType(product.getClassification().type())
                .displayOrder(product.getDisplayOrder())
                .releaseTimes(product.getReleaseTimesAsString())
                .deleted(product.isDeleted())
                .build();
    }

    public Product toDomain() {
        ProductInfo productInfo = ProductInfo.create(this.name, this.description, Image.create(this.profileImageUrl));
        SalesPolicy salesPolicy = new SalesPolicy(new Money(this.price), this.stock, this.status);
        Classification classification = new Classification(this.getProductType());
        List<DailyTime> dailyTimes = this.getReleaseTimes().stream()
                .map(DailyTime::of)
                .toList();

        return Product.builder()
                .id(this.getId())
                .bakeryId(this.getBakeryId())
                .productInfo(productInfo)
                .salesPolicy(salesPolicy)
                .classification(classification)
                .displayOrder(this.getDisplayOrder())
                .releaseTimes(dailyTimes)
                .deleted(this.isDeleted())
                .build();
    }
}
