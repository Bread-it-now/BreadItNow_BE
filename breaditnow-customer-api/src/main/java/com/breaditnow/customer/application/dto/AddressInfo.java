package com.breaditnow.customer.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressInfo {
    private final String sidoName;
    private final String gugunName;
    private final String dongName;
}
