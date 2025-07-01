package com.breaditnow.reservation.domain.model;

import com.breaditnow.reservation.application.dto.internal.OrdererInfo;
import lombok.Getter;

@Getter
public class Orderer {
    private Long customerId;
    private String nickname;
    private String phoneNumber;

    public Orderer(Long customerId, String nickname, String phoneNumber) {
        this.customerId = customerId;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public static Orderer create(OrdererInfo ordererInfo) {
        return new Orderer(ordererInfo.customerId(), ordererInfo.nickname(), ordererInfo.phoneNumber());
    }
}
