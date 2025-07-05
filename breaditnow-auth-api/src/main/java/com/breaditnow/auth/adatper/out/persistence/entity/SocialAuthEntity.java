package com.breaditnow.auth.adatper.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_auth", uniqueConstraints = {@UniqueConstraint(columnNames = {"provider", "providerId"})})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SocialAuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String providerId;
}
