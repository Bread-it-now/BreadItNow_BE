package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.customer.domain.Customer;
import lombok.Getter;

import java.util.List;

@Getter
public class Alert {
    private Long customerId;
    private List<Long> productIds;
    private DoNotDisturb doNotDisturb;

}
