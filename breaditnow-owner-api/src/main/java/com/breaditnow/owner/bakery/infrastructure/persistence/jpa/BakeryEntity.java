package com.breaditnow.owner.bakery.infrastructure.persistence.jpa;

import com.breaditnow.domain.global.entity.BaseEntity;
import com.breaditnow.owner.bakery.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

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

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private OperatingStatus operatingStatus;

    @ColumnDefault("0")
    private Integer favoriteCount;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone_number"))
    private PhoneNumber phoneNumber;

    private String openTime;
    private String introduction;

    @Embedded
    @AttributeOverride(name = "imageUrl", column = @Column(name = "profile_image_url"))
    private Image profileImage;

    @ElementCollection
    @CollectionTable(name = "bakery_image", joinColumns = @JoinColumn(name = "bakery_id"))
    @Builder.Default
    private List<Image> additionalImages = new ArrayList<>();

    private boolean deleted;

    public static BakeryEntity from(Bakery bakery) {
        return BakeryEntity.builder()
                .id(bakery.getBakeryId())
                .ownerId(bakery.getOwnerId())
                .name(bakery.getName())
                .address(bakery.getAddress())
                .operatingStatus(bakery.getOperatingStatus())
                .favoriteCount(bakery.getFavoriteCount())
                .phoneNumber(bakery.getPhoneNumber())
                .openTime(bakery.getOpenTime())
                .introduction(bakery.getIntroduction())
                .profileImage(bakery.getProfileImage())
                .additionalImages(bakery.getAdditionalImages())
                .deleted(bakery.isDeleted())
                .build();
    }

    public Bakery toDomain() {
        return Bakery.builder()
                .bakeryId(id)
                .ownerId(ownerId)
                .name(name)
                .address(this.address)
                .operatingStatus(operatingStatus)
                .favoriteCount(favoriteCount)
                .phoneNumber(this.phoneNumber)
                .openTime(openTime)
                .introduction(introduction)
                .profileImage(this.profileImage)
                .additionalImages(this.additionalImages)
                .deleted(deleted)
                .build();
    }
}
