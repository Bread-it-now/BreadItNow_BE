package com.breaditnow.bakery.infrastructure.jpa;

import com.breaditnow.common.domain.BaseEntity;
import com.breaditnow.bakery.domain.OperatingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "bakery")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BakeryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long ownerId;

    @Column(nullable = false)
    private String name;

    private String phone;

    private String introduction;

    private String profileImage;

    @Enumerated(STRING)
    private OperatingStatus operatingStatus;

    @ColumnDefault("0")
    private Integer favoriteCount;

    private Double latitude;
    private Double longitude;
    private String addressDescription;

    private boolean isActive;
}
