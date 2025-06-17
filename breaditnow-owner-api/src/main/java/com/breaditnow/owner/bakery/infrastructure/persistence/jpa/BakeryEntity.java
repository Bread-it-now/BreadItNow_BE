package com.breaditnow.owner.bakery.infrastructure.persistence.jpa;

import com.breaditnow.domain.global.entity.BaseEntity;
import com.breaditnow.owner.bakery.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "bakery")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BakeryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "bakery_id")
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    private String name;

    private String fullAddress;
    private String regionCode;
    private Double latitude;
    private Double longitude;

    @Enumerated(STRING)
    private OperatingStatus operatingStatus;

    @ColumnDefault("0")
    private Integer favoriteCount;

    private String phoneNumber;
    private String openTime;
    private String introduction;
    private String profileImageUrl;
    private boolean deleted;

    public static BakeryEntity from(Bakery bakery) {
        return BakeryEntity.builder()
                .id(bakery.getBakeryId())
                .ownerId(bakery.getOwnerId())
                .name(bakery.getName())
                .fullAddress(bakery.getAddress().fullAddress())
                .regionCode(bakery.getAddress().regionCode())
                .latitude(bakery.getAddress().latitude())
                .longitude(bakery.getAddress().longitude())
                .operatingStatus(bakery.getOperatingStatus())
                .favoriteCount(bakery.getFavoriteCount())
                .phoneNumber(bakery.getPhoneNumber() != null ? bakery.getPhoneNumber().value() : null)
                .openTime(bakery.getOpenTime())
                .introduction(bakery.getIntroduction())
                .profileImageUrl(bakery.getProfileImage().profileUrl())
                .deleted(bakery.isDeleted())
                .build();
    }

    public Bakery toDomain() {
        return Bakery.builder()
                .bakeryId(id)
                .ownerId(ownerId)
                .name(name)
                .address(new Address(regionCode, fullAddress, latitude, longitude))
                .operatingStatus(operatingStatus)
                .favoriteCount(favoriteCount)
                .phoneNumber(phoneNumber != null ? new PhoneNumber(phoneNumber) : null)
                .openTime(openTime)
                .introduction(introduction)
                .profileImage(new Image(profileImageUrl))
                .deleted(deleted)
                .build();
    }
}
