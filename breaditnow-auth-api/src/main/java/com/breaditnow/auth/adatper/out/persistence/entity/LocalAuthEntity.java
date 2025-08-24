package com.breaditnow.auth.adatper.out.persistence.entity;

import com.breaditnow.auth.domain.model.LocalAuth;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "local_auth")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalAuthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public LocalAuth toDomain() {
        return LocalAuth.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .accountId(this.accountId)
                .build();
    }

    public static LocalAuthEntity from(LocalAuth localAuth) {
        return LocalAuthEntity.builder()
                .id(localAuth.getId())
                .email(localAuth.getEmail())
                .password(localAuth.getPassword())
                .accountId(localAuth.getAccountId())
                .build();
    }
}
