package com.breaditnow.customer.adapter.out.persistence.entity;

import com.breaditnow.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "customer_region")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class CustomerRegionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long customerId;

    private String sidoCode;
    private String gugunCode;

    public CustomerRegionEntity(Long customerId, String sidoCode, String gugunCode) {
        this.customerId = customerId;
        this.sidoCode = sidoCode;
        this.gugunCode = gugunCode;
    }
}
