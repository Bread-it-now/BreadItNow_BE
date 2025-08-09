package com.breaditnow.customer.customer.adapter.out.persistence.entity;

import com.breaditnow.customer.common.domain.BaseEntity;
import com.breaditnow.customer.customer.domain.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "customer")
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
public class CustomerEntity extends BaseEntity {
    @Id
    private Long id;

    private String fcmToken;

    @Column(unique = true)
    private String nickname;

    private String phone;

    private String profileImage;

    public CustomerEntity(Customer customer) {
        this.id = customer.getId();
        this.fcmToken = customer.getFcmToken();
        this.nickname = customer.getNickname();
        this.phone = customer.getPhone();
        this.profileImage = customer.getProfileImageUrl();
    }

    public Customer toDomain() {
        return Customer.builder()
                .id(this.id)
                .nickname(this.nickname)
                .phone(this.phone)
                .profileImageUrl(this.profileImage)
                .fcmToken(this.fcmToken)
                .build();
    }
}
