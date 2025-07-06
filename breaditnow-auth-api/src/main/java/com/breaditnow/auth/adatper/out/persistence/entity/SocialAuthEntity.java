package com.breaditnow.auth.adatper.out.persistence.entity;

import com.breaditnow.auth.domain.model.Provider;
import com.breaditnow.auth.domain.model.SocialAuth;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "social_auth", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider", "providerId"})
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(nullable = false)
    private String providerId;

    public SocialAuth toDomain() {
        return SocialAuth.builder()
                .id(this.id)
                .provider(this.provider)
                .providerId(this.providerId)
                .accountId(this.accountId)
                .build();
    }

    public static SocialAuthEntity from(SocialAuth socialAuth) {
        return SocialAuthEntity.builder()
                .id(socialAuth.getId())
                .provider(socialAuth.getProvider())
                .providerId(socialAuth.getProviderId())
                .accountId(socialAuth.getAccountId())
                .build();
    }
}
