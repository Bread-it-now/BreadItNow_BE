package com.breaditnow.customer.customer.infrastructure.entity;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.domain.Provider;
import com.breaditnow.domain.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.lang.Boolean.TRUE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "customer")
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class CustomerEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private Provider provider;

    private String oauth2Id;

    private String fcmToken;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String phone;

    private String profileImage;

    private Boolean initialSetup;

    public CustomerEntity(Customer customer) {
        this.id = customer.getId();
        this.provider = customer.getProvider();
        this.oauth2Id = customer.getOauth2Id();
        this.fcmToken = customer.getFcmToken();
        this.email = customer.getEmail();
        this.password = customer.getPassword();
        this.nickname = customer.getNickname();
        this.phone = customer.getPhone();
        this.profileImage = customer.getProfileImageUrl();
        this.initialSetup = customer.isInitialSetup();
    }

    public Customer toDomain() {
        return Customer.builder()
                .id(this.id)
                .nickname(this.nickname)
                .phone(this.phone)
                .profileImageUrl(this.profileImage)
                .provider(this.provider)
                .oauth2Id(this.oauth2Id)
                .email(this.email)
                .password(this.password)
                .fcmToken(this.fcmToken)
                .initialSetup(TRUE.equals(this.initialSetup))
                .build();
    }
}
