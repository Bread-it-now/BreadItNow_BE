package com.breaditnow.auth.adatper.out.persistence.entity;

import com.breaditnow.auth.domain.model.Provider;
import com.breaditnow.auth.domain.model.SocialAuth;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

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
                .accountId(this.account.getId())
                .build();
    }

    public static SocialAuthEntity from(SocialAuth socialAuth, AccountEntity accountEntity) {
        return SocialAuthEntity.builder()
                .id(socialAuth.getId())
                .provider(socialAuth.getProvider())
                .providerId(socialAuth.getProviderId())
                .account(accountEntity)
                .build();
    }
}
