package com.breaditnow.customer.reservation.domain;

import lombok.Getter;

@Getter
public class Orderer {
    private Long ordererId;
    private String ordererName;
    private String ordererPhoneNumber;

    public Orderer(Long ordererId, String ordererName, String ordererPhoneNumber) {
        this.ordererId = ordererId;
        this.ordererName = ordererName;
        this.ordererPhoneNumber = ordererPhoneNumber;
    }
}
