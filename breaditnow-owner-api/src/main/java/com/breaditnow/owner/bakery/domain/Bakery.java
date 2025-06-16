package com.breaditnow.owner.bakery.domain;

import lombok.Getter;

import static com.breaditnow.owner.bakery.domain.OperatingStatus.OPEN;

@Getter
public class Bakery {
    private Long bakeryId;
    private String name;
    private Long ownerId;
    private Address address;
    private PhoneNumber phoneNumber;
    private Image profileImage;
    private OperatingStatus operatingStatus;
    private String openTime;
    private String introduction;

    public Bakery(Long ownerId, String name, Address address, PhoneNumber phoneNumber, String openTime, String introduction, Image profileImage) {
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.openTime = openTime;
        this.introduction = introduction;
        this.profileImage = profileImage;
        this.operatingStatus = OPEN;
    }
}
