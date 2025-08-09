package com.breaditnow.product.infrastructure.jpa.entity;

import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.common.domain.Money;
import com.breaditnow.common.domain.BaseEntity;
import com.breaditnow.product.domain.Product;
import com.breaditnow.product.domain.ProductType;
import com.breaditnow.product.infrastructure.jpa.ReleaseTimesConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    private Integer price;

    private String imageUrl;

    private String description;

    private Integer displayOrder;

    private boolean isActive;

    private boolean isHidden;

    @ColumnDefault("0")
    private Integer favoriteCount;

    @Convert(converter = ReleaseTimesConverter.class)
    private List<String> releaseTimes;

    @Enumerated(STRING)
    private ProductType type;

    public static ProductEntity from(Product product) {
        List<String> releaseTimes = product.getReleaseTimes().stream().
                map(DailyTime::toString)
                .toList();

        return new ProductEntity(
                product.getId(),
                product.getBakeryId(),
                product.getName(),
                product.getStock(),
                product.getPrice().getAmount(),
                product.getImageUrl(),
                product.getDescription(),
                product.getDisplayOrder(),
                product.isActive(),
                product.isHidden(),
                product.getFavoriteCount(),
                releaseTimes,
                product.getType()
        );
    }

    public Product toDomain() {
        List<DailyTime> dailyTimes = this.releaseTimes.stream()
                .map(DailyTime::of)
                .toList();

        return Product.builder()
                .id(this.id)
                .bakeryId(this.bakeryId)
                .name(this.name)
                .stock(this.stock)
                .price(new Money(this.price))
                .imageUrl(this.imageUrl)
                .description(this.description)
                .displayOrder(this.displayOrder)
                .isActive(this.isActive)
                .isHidden(this.isHidden)
                .favoriteCount(this.favoriteCount)
                .releaseTimes(dailyTimes)
                .type(this.type)
                .build();
    }
}