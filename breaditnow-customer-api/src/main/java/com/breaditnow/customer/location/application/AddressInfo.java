package com.breaditnow.customer.location.application;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressInfo {
    private final String sidoName;
    private final String gugunName;
    private final String dongName;
}
