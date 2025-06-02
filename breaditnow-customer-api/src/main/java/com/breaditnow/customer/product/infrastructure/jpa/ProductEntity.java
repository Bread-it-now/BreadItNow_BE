package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.common.domain.ReleaseTime;
import com.breaditnow.customer.common.infrastructure.entity.MoneyConverter;
import com.breaditnow.customer.common.infrastructure.entity.ReleaseTimeConverter;
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
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private Integer favoriteCounter;

    @ColumnDefault("0")
    private Integer reservationCounter;

    @Convert(converter = ReleaseTimeConverter.class)
    private List<ReleaseTime> releaseTimes;

    @Enumerated(STRING)
    private ProductType type;
}
