package com.breaditnow.auth.adatper.out.persistence.entity;

import com.breaditnow.auth.domain.model.AccountStatus;
import com.breaditnow.common.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(STRING)
    @Column(nullable = false)
    private AccountStatus status;
}
