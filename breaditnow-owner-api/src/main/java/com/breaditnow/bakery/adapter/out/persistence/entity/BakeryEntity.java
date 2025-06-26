package com.breaditnow.bakery.adapter.out.persistence.entity;

import com.breaditnow.bakery.domain.model.*;
import com.breaditnow.common.jpa.BaseEntity;
import com.breaditnow.image.domain.Image;
import com.breaditnow.location.domain.model.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "bakery")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BakeryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "bakery_id")
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    private String name;

    private String regionCode;
    private String fullAddress;
    private double latitude;
    private double longitude;

    @Enumerated(STRING)
    private OperatingStatus operatingStatus;

    @ColumnDefault("0")
    private Integer favoriteCount;

    @Column(name = "phone_number")
    private String phoneNumber;


    private String openTime;
    private String introduction;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @ElementCollection
    @CollectionTable(name = "bakery_image", joinColumns = @JoinColumn(name = "bakery_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> additionalImages = new ArrayList<>();

    private boolean deleted;

    public static BakeryEntity from(Bakery bakery) {
        return BakeryEntity.builder()
                .id(bakery.getBakeryId())
                .ownerId(bakery.getOwnerId())
                .name(bakery.getName())
                .regionCode(bakery.getAddress().regionCode())
                .fullAddress(bakery.getAddress().fullAddress())
                .latitude(bakery.getAddress().latitude())
                .longitude(bakery.getAddress().longitude())
                .operatingStatus(bakery.getOperatingStatus())
                .favoriteCount(bakery.getFavoriteCount())
                .phoneNumber(bakery.getPhoneNumber().value())
                .openTime(bakery.getOpenTime())
                .introduction(bakery.getIntroduction())
                .profileImageUrl(bakery.getProfileImage() == null ? null : bakery.getProfileImage().imageUrl())
                .additionalImages(bakery.getAdditionalImages().stream()
                        .map(Image::imageUrl)
                        .collect(Collectors.toList()))
                .deleted(bakery.isDeleted())
                .build();
    }

    public Bakery toDomain() {
        return Bakery.builder()
                .bakeryId(id)
                .ownerId(ownerId)
                .name(name)
                .address(Address.create(this.regionCode, this.fullAddress, this.latitude, this.longitude))
                .operatingStatus(operatingStatus)
                .favoriteCount(favoriteCount)
                .phoneNumber(PhoneNumber.create(this.phoneNumber))
                .openTime(openTime)
                .introduction(introduction)
                .profileImage(Image.create(this.profileImageUrl))
                .additionalImages(this.additionalImages.stream()
                        .map(Image::create)
                        .toList())
                .deleted(deleted)
                .build();
    }
}
