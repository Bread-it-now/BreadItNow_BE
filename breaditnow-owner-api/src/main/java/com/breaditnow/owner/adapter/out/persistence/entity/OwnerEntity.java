package com.breaditnow.owner.adapter.out.persistence.entity;

import com.breaditnow.owner.domain.model.Owner;
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
    private Long id;

    private String fcmToken;

    @Column(unique = true)
    private String nickname;

    public Owner toDomain() {
        return Owner.builder()
                .id(getId())
                .fcmToken(getFcmToken())
                .nickname(getNickname())
                .build();
    }

    public static OwnerEntity from(Owner domainEntity) {
        return new OwnerEntity(
                domainEntity.getId(),
                domainEntity.getFcmToken(),
                domainEntity.getNickname()
        );
    }
}