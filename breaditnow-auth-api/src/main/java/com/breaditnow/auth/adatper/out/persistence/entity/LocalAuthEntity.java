package com.breaditnow.auth.adatper.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "local_auth")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LocalAuthEntity {
    @Id
    private Long id;

    @OneToOne(fetch = LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private AccountEntity account;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
}
