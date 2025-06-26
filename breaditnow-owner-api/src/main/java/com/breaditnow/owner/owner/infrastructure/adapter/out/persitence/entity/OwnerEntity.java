package com.breaditnow.owner.owner.infrastructure.adapter.out.persitence.entity;

import com.breaditnow.owner.owner.domain.model.Owner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "owner")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OwnerEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String fcmToken;

    public Owner toDomain() {
        return Owner.builder()
                .id(getId())
                .email(getEmail())
                .password(getPassword())
                .fcmToken(getFcmToken())
                .build();
    }

    public static OwnerEntity from(Owner domainEntity) {
        return new OwnerEntity(
                domainEntity.getId(),
                domainEntity.getEmail(),
                domainEntity.getPassword(),
                domainEntity.getFcmToken()
        );
    }
}
