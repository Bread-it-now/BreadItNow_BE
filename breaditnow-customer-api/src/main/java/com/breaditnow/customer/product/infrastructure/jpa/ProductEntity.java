package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.common.domain.ReleaseTime;
import com.breaditnow.customer.common.infrastructure.entity.MoneyConverter;
import com.breaditnow.customer.common.infrastructure.entity.ReleaseTimeConverter;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.domain.ProductType;
import com.breaditnow.domain.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long bakeryId;

    private String name;

    private Integer stock;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    private String imageUrl;

    private String description;

    private Integer displayOrder;

    private boolean isActive;

    private boolean isHidden;

    @ColumnDefault("0")
    private Integer favoriteCount;

    @ColumnDefault("0")
    private Integer reservationCount;

    @Convert(converter = ReleaseTimeConverter.class)
    private List<ReleaseTime> releaseTimes;

    @Enumerated(STRING)
    private ProductType type;

    public static ProductEntity from(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getBakeryId(),
                product.getName(),
                product.getStock(),
                product.getPrice(),
                product.getImageUrl(),
                product.getDescription(),
                product.getDisplayOrder(),
                product.isActive(),
                product.isHidden(),
                product.getFavoriteCount(),
                product.getReservationCount(),
                product.getReleaseTimes(),
                product.getType()
        );
    }

    public Product toDomain() {
        return Product.builder()
                .id(this.id)
                .bakeryId(this.bakeryId)
                .name(this.name)
                .stock(this.stock)
                .price(this.price)
                .imageUrl(this.imageUrl)
                .description(this.description)
                .displayOrder(this.displayOrder)
                .isActive(this.isActive)
                .isHidden(this.isHidden)
                .favoriteCount(this.favoriteCount)
                .reservationCount(this.reservationCount)
                .releaseTimes(this.releaseTimes)
                .type(this.type)
                .build();
    }
}