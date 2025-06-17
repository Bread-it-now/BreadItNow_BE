package com.breaditnow.owner.bakery.domain;

import com.breaditnow.domain.global.exception.DomainException;
import com.breaditnow.owner.global.exception.OwnerException;
import lombok.Builder;
import lombok.Getter;

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
    private OperatingStatus operatingStatus;
    private Integer favoriteCount;
    private String openTime;
    private String introduction;
    private boolean deleted;

    @Builder
    private Bakery(Long bakeryId, String name, Long ownerId, Address address, PhoneNumber phoneNumber, Image profileImage, OperatingStatus operatingStatus, Integer favoriteCount, String openTime, String introduction, boolean deleted) {
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
        this.deleted = deleted;
    }

    public static Bakery create(Long ownerId, String name, Address address, PhoneNumber phoneNumber, Image profileImage, String openTime, String introduction) {
        return Bakery.builder()
                .ownerId(ownerId)
                .name(name)
                .address(address)
                .phoneNumber(phoneNumber)
                .profileImage(profileImage)
                .operatingStatus(OperatingStatus.OPEN)
                .favoriteCount(0)
                .deleted(false)
                .openTime(openTime)
                .introduction(introduction)
                .build();
    }

    public void delete(Long ownerId) {
        validateOwner(ownerId);
        validateActive();
        this.deleted = true;
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
