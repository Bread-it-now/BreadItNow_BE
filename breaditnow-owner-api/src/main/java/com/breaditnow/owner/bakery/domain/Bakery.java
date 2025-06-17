package com.breaditnow.owner.bakery.domain;

import com.breaditnow.domain.global.exception.DomainException;
import com.breaditnow.owner.global.exception.OwnerException;
import io.micrometer.common.util.StringUtils;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BAKERY_INACTIVE;
import static com.breaditnow.owner.global.exception.OwnerErrorCode.UNAUTHORIZED_BAKERY_ACCESS;

@Getter
public class Bakery {
    private Long bakeryId;
    private String name;
    private Long ownerId;
    private Address address;
    private PhoneNumber phoneNumber;
    private Image profileImage;
    private List<Image> additionalImages;
    private OperatingStatus operatingStatus;
    private Integer favoriteCount;
    private String openTime;
    private String introduction;
    private boolean deleted;

    @Builder
    private Bakery(Long bakeryId, String name, Long ownerId, Address address, PhoneNumber phoneNumber, Image profileImage, List<Image> additionalImages, OperatingStatus operatingStatus, Integer favoriteCount, String openTime, String introduction, boolean deleted) {
        this.bakeryId = bakeryId;
        this.name = name;
        this.ownerId = ownerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.operatingStatus = operatingStatus;
        this.favoriteCount = favoriteCount;
        this.openTime = openTime;
        this.introduction = introduction;
        this.additionalImages = additionalImages != null ? additionalImages : new ArrayList<>();
        this.deleted = deleted;
    }

    public static Bakery create(Long ownerId, String name, Address address, PhoneNumber phoneNumber, Image profileImage, String openTime, String introduction) {
        return Bakery.builder()
                .ownerId(ownerId)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .profileImage(profileImage)
                .additionalImages(new ArrayList<>())
                .operatingStatus(OperatingStatus.OPEN)
                .favoriteCount(0)
                .deleted(false)
                .openTime(openTime)
                .introduction(introduction)
                .build();
    }

    public void update(Long ownerId, String name, String openTime, String introduction) {
        validateOwner(ownerId);
        validateActive();
        if (StringUtils.isNotBlank(name)) {
            this.name = name;
        }
        if (StringUtils.isNotBlank(openTime)) {
            this.openTime = openTime;
        }
        if (StringUtils.isNotBlank(introduction)) {
            this.introduction = introduction;
        }
    }

    public void updateOperatingStatus(Long ownerId, OperatingStatus newStatus) {
        validateOwner(ownerId);
        validateActive();
        this.operatingStatus = newStatus;
    }

    public void delete(Long ownerId) {
        validateOwner(ownerId);
        validateActive();
        this.deleted = true;
    }

    public void updateProfileImage(Long ownerId, Image profileImage) {
        validateOwner(ownerId);
        validateActive();
        this.profileImage = profileImage;
    }

    public void addAdditionalImages(Long ownerId, List<Image> newImages) {
        validateOwner(ownerId);
        validateActive();
        if (newImages != null) {
            this.additionalImages.addAll(newImages);
        }
    }

    private void validateOwner(Long ownerId) {
        if (!this.getOwnerId().equals(ownerId)) {
            throw new OwnerException(UNAUTHORIZED_BAKERY_ACCESS);
        }
    }

    private void validateActive() {
        if (this.isDeleted()) {
            throw new DomainException(BAKERY_INACTIVE);
        }
    }
}
